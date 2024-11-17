package com.dino.ander.movieapp.ui.utils

import com.dino.ander.movieapp.domain.model.MovieDetails

sealed class UiState {
    data object Loading : UiState()

    data class Success(
        val data: MovieDetails?
    ) : UiState()

    data class Error(
        val message: Int
    ) : UiState()
}