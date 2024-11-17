package com.dino.ander.movieapp.di

import android.content.Context
import androidx.room.Room
import com.dino.ander.movieapp.room.MovieDataBase
import com.dino.ander.movieapp.room.dao.MovieDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDataBase {
        return Room.databaseBuilder(context, MovieDataBase::class.java, "moves.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(dataBase: MovieDataBase): MovieDetailDao {
        return dataBase.movieDetailDao()
    }

}