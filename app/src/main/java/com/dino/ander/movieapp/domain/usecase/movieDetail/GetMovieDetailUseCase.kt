package com.dino.ander.movieapp.domain.usecase.movieDetail

import com.dino.ander.movieapp.domain.model.MovieDetails
import com.dino.ander.movieapp.domain.state.Result
import com.dino.ander.movieapp.domain.repository.MovieRepository
import com.dino.ander.movieapp.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : UseCase<Int, Flow<Result<MovieDetails?>>> {
    override fun execute(params: Int): Flow<Result<MovieDetails?>> {
        return movieRepository.getMovieDetailFlow(params).mapNotNull { response ->
            val result = response.data
            return@mapNotNull when (response) {
                is Result.Success -> Result.Success(result)
                is Result.Error -> Result.Error(response.error, result)
                is Result.Loading -> Result.Loading(result)
            }
        }
    }
}