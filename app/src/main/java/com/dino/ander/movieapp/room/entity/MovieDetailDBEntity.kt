package com.dino.ander.movieapp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieDetails")
data class MovieDetailDBEntity(
    @PrimaryKey val id: Int,
    val overview: String="",
    val posterPath: String? ="",
    val releaseDate: String = "",
    val title: String = "",
    val popularity: Double = 0.00,
    val voteAverage: Double = 0.00,
    val voteCount: Int = 0,
    val status: String = "",
    val modifiedAt: Long = 0
) {
}