package com.dino.ander.movieapp.ui.utils

import androidx.recyclerview.widget.DiffUtil
import com.dino.ander.movieapp.domain.model.Movie

class DiffUtilMovieItem : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
}