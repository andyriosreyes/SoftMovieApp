package com.dino.ander.movieapp.ui.feature.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.dino.ander.movieapp.R
import com.dino.ander.movieapp.databinding.FragmentMovieListBinding
import com.dino.ander.movieapp.ui.utils.MoveListItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private val viewModel by viewModels<MoviesListViewModel>()
    private val movieAdapter by lazy {
        MovieListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(android.R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        sharedElementReturnTransition = context?.let {
            TransitionInflater.from(it)
                .inflateTransition(R.transition.shared_element_transition)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        setupMovieList()
        observeUI()
        binding.recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
        binding.swiperefresh.setOnRefreshListener {
            movieAdapter.refresh()
        }
    }

    private fun setupMovieList() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = movieAdapter
            movieAdapter.clickListener = { movie ->
                val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie)
                findNavController().navigate(action)
            }

            addItemDecoration(
                MoveListItemDecoration(
                resources.getDimension(R.dimen._8dp).toInt()
            )
            )
        }
    }

    private fun observeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviesPaged.collectLatest { movieAdapter.submitData(it) }
        }

        lifecycleScope.launch {
            movieAdapter.loadStateFlow.collectLatest { loadStates ->
                val loadState = loadStates.refresh

                binding.swiperefresh.isRefreshing = loadState is LoadState.Loading

                if (loadState is LoadState.Error) showError(loadState.error.localizedMessage)
                if (loadState !is LoadState.Loading) {
                    binding.noResults.visibility =
                        if (movieAdapter.itemCount == 0) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun showError(message: String?) {
        binding.noResults.text = message
    }
}