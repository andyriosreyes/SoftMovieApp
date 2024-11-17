package com.dino.ander.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MoviesEntity(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieEntity>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)