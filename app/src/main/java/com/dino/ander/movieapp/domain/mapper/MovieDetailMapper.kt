package com.dino.ander.movieapp.domain.mapper

import com.dino.ander.movieapp.data.model.MovieDetailEntity
import com.dino.ander.movieapp.domain.model.MovieDetails

fun MovieDetails.toEntity() = MovieDetailEntity(
    id = id,
    overview = overview,
    posterPath =posterPath,
    releaseDate = releaseDate,
    title = title,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    status = status,
    modifiedAt = modifiedAt
)