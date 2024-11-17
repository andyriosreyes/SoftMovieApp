package com.dino.ander.movieapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dino.ander.movieapp.room.entity.MovieDetailDBEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieDetailDBEntity)
    @Query("SELECT * FROM movieDetails WHERE id=:id")
    fun getMovie(id: Int): Flow<MovieDetailDBEntity?>

    fun getMovieDistinctUntilChanged(id: Int) =
        getMovie(id).distinctUntilChanged()
}