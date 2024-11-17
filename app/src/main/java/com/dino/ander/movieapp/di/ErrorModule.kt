package com.dino.ander.movieapp.di

import com.dino.ander.movieapp.data.error.ErrorHandlerImpl
import com.dino.ander.movieapp.domain.state.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {

    @Binds
    abstract fun bindErrorHandler(errorHandlerImpl: ErrorHandlerImpl): ErrorHandler
}