package com.dino.ander.movieapp.ui.feature.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dino.ander.movieapp.domain.usecase.movie.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase) : ViewModel() {

    val moviesPaged = getMoviesUseCase.execute(Unit).flowOn(Dispatchers.IO).cachedIn(viewModelScope)

}