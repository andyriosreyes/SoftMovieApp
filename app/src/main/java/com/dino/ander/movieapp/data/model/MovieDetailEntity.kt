package com.dino.ander.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("modifiedAt")
    val modifiedAt: Long = 0
) {
}