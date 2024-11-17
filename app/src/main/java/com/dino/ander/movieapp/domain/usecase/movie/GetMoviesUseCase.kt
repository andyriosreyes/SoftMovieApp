package com.dino.ander.movieapp.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.map
import com.dino.ander.movieapp.data.mapper.toDomain
import com.dino.ander.movieapp.domain.model.Movie
import com.dino.ander.movieapp.domain.repository.MovieRepository
import com.dino.ander.movieapp.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : UseCase<Unit, Flow<@JvmSuppressWildcards PagingData<Movie>>> {

    override fun execute(params: Unit) = movieRepository.getPagedMovies().map { pagingData ->
        pagingData.map {
            it.toDomain()
        }
    }
}