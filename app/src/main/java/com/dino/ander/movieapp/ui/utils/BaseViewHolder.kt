package com.dino.ander.movieapp.ui.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T: Any?>(itemView: ViewBinding) : RecyclerView.ViewHolder(itemView.root) {
    abstract fun bind(item: T, position: Int)
}

