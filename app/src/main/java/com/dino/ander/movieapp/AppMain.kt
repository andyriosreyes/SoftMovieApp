package com.dino.ander.movieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppMain : Application() {
    companion object {
        lateinit var app: AppMain
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}