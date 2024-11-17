package com.dino.ander.movieapp.ui.feature.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dino.ander.movieapp.R
import com.dino.ander.movieapp.domain.state.Result
import com.dino.ander.movieapp.domain.usecase.movieDetail.GetMovieDetailUseCase
import com.dino.ander.movieapp.ui.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase): ViewModel() {

    private val _movieFlow = MutableStateFlow<UiState>(UiState.Loading)
    val movieFlow: StateFlow<UiState> = _movieFlow.asStateFlow()

    fun getMovieDetail(movieId: Int){
        viewModelScope.launch {
            getMovieDetailUseCase.execute(movieId).flowOn(Dispatchers.IO).collect {
                result ->
                _movieFlow.update {
                    when (result) {
                        is Result.Loading -> UiState.Loading
                        is Result.Success -> UiState.Success(result.data)
                        is Result.Error -> UiState.Error(messageError(result.error))
                    }
                }
            }
        }
    }
    private fun messageError(error: Result.ErrorType?) = when (error) {
        is Result.ErrorType.DatabaseError -> R.string.database_error
        is Result.ErrorType.HttpError -> R.string.server_error
        is Result.ErrorType.IOError -> R.string.connection_error
        is Result.ErrorType.Unknown -> R.string.generic_error
        else -> R.string.connection_error
    }
}