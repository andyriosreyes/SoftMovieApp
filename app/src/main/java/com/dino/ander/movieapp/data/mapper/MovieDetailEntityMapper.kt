package com.dino.ander.movieapp.data.mapper

import com.dino.ander.movieapp.data.model.MovieDetailEntity
import com.dino.ander.movieapp.domain.model.MovieDetails
import com.dino.ander.movieapp.room.entity.MovieDetailDBEntity

fun MovieDetailDBEntity.toData() = MovieDetailEntity(
    id = id,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    popularity = 0.00,
    voteAverage = 0.00,
    voteCount = voteCount,
    status = status,
    modifiedAt = modifiedAt
)

fun MovieDetailEntity.toDB() = MovieDetailDBEntity(
    id = id,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    status = status,
    modifiedAt = modifiedAt
)

fun MovieDetailEntity.toDomain() = MovieDetails(
    id = id,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    status = status,
    modifiedAt = modifiedAt
)