package com.dino.ander.movieapp.domain.model

data class MovieDetails(
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val status: String,
    val modifiedAt: Long=0
)