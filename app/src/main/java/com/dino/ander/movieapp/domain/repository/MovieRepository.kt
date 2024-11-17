package com.dino.ander.movieapp.domain.repository

import androidx.paging.PagingData
import com.dino.ander.movieapp.data.model.MovieEntity
import com.dino.ander.movieapp.domain.model.MovieDetails
import com.dino.ander.movieapp.domain.state.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getPagedMovies(): Flow<PagingData<MovieEntity>>
    fun getMovieDetailFlow(movieId: Int): Flow<Result<MovieDetails?>>
    fun loginFlow(user: String, pass: String): Flow<Result<Boolean>>
}