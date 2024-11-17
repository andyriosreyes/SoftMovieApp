package com.dino.ander.movieapp.ui.utils

sealed class UIStateLogin {
    data object Loading : UIStateLogin()

    data object Empty : UIStateLogin()

    data class Success(
        val data: Boolean
    ) : UIStateLogin()

    data class Error(
        val message: Int
    ) : UIStateLogin()
}