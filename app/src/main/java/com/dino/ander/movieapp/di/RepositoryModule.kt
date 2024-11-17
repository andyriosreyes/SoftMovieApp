package com.dino.ander.movieapp.di

import com.dino.ander.movieapp.data.network.ApiService
import com.dino.ander.movieapp.data.repository.MovieRepositoryImp
import com.dino.ander.movieapp.domain.repository.MovieRepository
import com.dino.ander.movieapp.domain.state.ErrorHandler
import com.dino.ander.movieapp.room.dao.MovieDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepositoryImp(
        apiService: ApiService,
        movieDetailDao: MovieDetailDao,
        errorHandler: ErrorHandler
    ): MovieRepository {
        return MovieRepositoryImp(apiService,movieDetailDao,errorHandler)
    }
}