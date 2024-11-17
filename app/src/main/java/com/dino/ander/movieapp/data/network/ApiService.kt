package com.dino.ander.movieapp.data.network

import com.dino.ander.movieapp.data.APIUrl.GET_DETAIL_MOVIE
import com.dino.ander.movieapp.data.APIUrl.GET_MOVIES
import com.dino.ander.movieapp.data.model.MovieDetailEntity
import com.dino.ander.movieapp.data.model.MoviesEntity
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(GET_MOVIES)
    suspend fun getMovies(
        @Query("page") page: Int
    ): Response<MoviesEntity>

    @GET(GET_DETAIL_MOVIE)
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailEntity>

}