package com.dino.ander.movieapp.data.error

import com.dino.ander.movieapp.domain.state.ErrorHandler
import com.dino.ander.movieapp.domain.state.Result
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor() : ErrorHandler {

    override fun getError(throwable: Throwable): Result.ErrorType {
        return when (throwable) {
            is IOException -> Result.ErrorType.IOError(throwable)
            is HttpException -> Result.ErrorType.HttpError(throwable, throwable.code())
            else -> Result.ErrorType.Unknown(throwable)
        }
    }

    override fun getApiError(statusCode: Int, throwable: Throwable?): Result.ErrorType {
        return Result.ErrorType.HttpError(throwable, statusCode)
    }
}