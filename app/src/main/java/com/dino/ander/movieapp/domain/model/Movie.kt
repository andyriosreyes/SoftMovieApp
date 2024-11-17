package com.dino.ander.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean = false,
    val backdropPath: String? = null,
    val id: Int = 0,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
): Parcelable {
    companion object {
        const val LOW_RES_PREFIX = "https://image.tmdb.org/t/p/w185/"
        const val HIGH_RES_PREFIX = "https://image.tmdb.org/t/p/w500/"
    }
}