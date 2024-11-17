package com.dino.ander.movieapp.ui.feature.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dino.ander.movieapp.R
import com.dino.ander.movieapp.databinding.ItemMovieBinding
import com.dino.ander.movieapp.domain.model.Movie
import com.dino.ander.movieapp.ui.utils.BasePagingAdapter
import com.dino.ander.movieapp.ui.utils.DiffUtilMovieItem

class MovieListAdapter : BasePagingAdapter<Movie, ItemMovieBinding>(DiffUtilMovieItem()) {
    var clickListener: ((Movie) -> Unit)? = null

    override val itemViewType = R.layout.item_movie

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemMovieBinding =
        ItemMovieBinding::inflate

    override fun bind(binding: ItemMovieBinding, item: Movie, position: Int) {
        val highResImage = Movie.HIGH_RES_PREFIX + item.posterPath
        val lowResImage = Movie.LOW_RES_PREFIX + item.posterPath
        val transName = highResImage + getItemViewType(position)
        binding.moveListItemImage.apply {
            Glide.with(context)
                .load(lowResImage)
                .placeholder(R.drawable.media_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

            transitionName = transName
        }
        binding.moveListItemImage.setOnClickListener {
            clickListener?.invoke(item)
        }
    }
}
