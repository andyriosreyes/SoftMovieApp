package com.dino.ander.movieapp.data.mapper

import com.dino.ander.movieapp.data.model.MovieEntity
import com.dino.ander.movieapp.domain.model.Movie

fun MovieEntity.toDomain() = Movie(
    adult = adult,
    backdropPath = backdropPath,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)