package com.dino.ander.movieapp.domain.usecase.login

import com.dino.ander.movieapp.domain.repository.MovieRepository
import com.dino.ander.movieapp.domain.state.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
){

    fun execute(user: String, pass: String): Flow<Result<Boolean>> {
        return movieRepository.loginFlow(user,pass).mapNotNull { response ->
            val result = response.data
            return@mapNotNull when (response) {
                is Result.Success -> Result.Success(result ?: false)
                is Result.Error -> Result.Error(response.error, result)
                is Result.Loading -> Result.Loading(result)
            }
        }
    }


}