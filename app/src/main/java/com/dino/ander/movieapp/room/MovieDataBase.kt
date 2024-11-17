package com.dino.ander.movieapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dino.ander.movieapp.room.dao.MovieDetailDao
import com.dino.ander.movieapp.room.entity.MovieDetailDBEntity

@Database(
    entities = [MovieDetailDBEntity::class],
    version = 1,
    exportSchema = false
)

abstract class MovieDataBase : RoomDatabase(){
    abstract fun movieDetailDao() : MovieDetailDao
}