package com.dino.ander.movieapp.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModuleTest {

    @Provides
    @Named("test_db")
    fun provideDatabaseTest(@ApplicationContext context: Context) = Room.inMemoryDatabaseBuilder(
        context, MovieDataBase::class.java
    ).allowMainThreadQueries().build()
}