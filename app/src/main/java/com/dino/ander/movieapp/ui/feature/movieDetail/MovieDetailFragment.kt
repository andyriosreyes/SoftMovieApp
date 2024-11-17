package com.dino.ander.movieapp.ui.feature.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dino.ander.movieapp.ui.base.BaseFragment
import com.bumptech.glide.Glide
import com.dino.ander.movieapp.R
import com.dino.ander.movieapp.databinding.FragmentMovieDetailBinding
import com.dino.ander.movieapp.domain.model.Movie
import com.dino.ander.movieapp.domain.model.MovieDetails
import com.dino.ander.movieapp.ui.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailFragmentArgs by navArgs()
    lateinit var movie : Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = args.moviedata
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieDetail(movie.id)
        observeUI()
    }

    private fun observeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieFlow.collect {
                when (it){
                    is UiState.Loading -> {
                        baseActivity.showProgress()
                    }
                    is UiState.Success-> {
                        baseActivity.dismissProgress()
                        showMovie(it.data)
                    }
                    is UiState.Error -> {
                        baseActivity.dismissProgress()
                        showError(it.message)
                    }

                }
            }
        }
    }

    private fun showMovie(data: MovieDetails?){
        binding.tvDate.text = data?.releaseDate
        binding.tvMovieTitle.text = data?.title
        binding.tvRate.text = data?.voteAverage.toString()
        binding.tvSinopsis.text = data?.overview
        val lowResImage = Movie.LOW_RES_PREFIX + data?.posterPath
        binding.ivPoster.apply {
            Glide.with(context)
                .load(lowResImage)
                .placeholder(R.drawable.media_placeholder)
                .into(this)
        }

    }

    private fun showError(message: Int) {
        Toast.makeText(requireContext(),getString(message),Toast.LENGTH_LONG).show()
    }

}