package com.dino.ander.movieapp.di

import android.content.Context
import com.dino.ander.movieapp.AppMain
import com.dino.ander.movieapp.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun providesAppApplicationInstance(@ApplicationContext context: Context): AppMain {
        return context as AppMain
    }
}