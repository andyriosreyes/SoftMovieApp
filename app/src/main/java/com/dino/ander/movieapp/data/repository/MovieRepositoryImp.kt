package com.dino.ander.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dino.ander.movieapp.data.mapper.toDB
import com.dino.ander.movieapp.data.mapper.toData
import com.dino.ander.movieapp.data.mapper.toDomain
import com.dino.ander.movieapp.data.model.MovieDetailEntity
import com.dino.ander.movieapp.data.model.MovieEntity
import com.dino.ander.movieapp.data.network.ApiService
import com.dino.ander.movieapp.domain.model.MovieDetails
import com.dino.ander.movieapp.domain.repository.MovieRepository
import com.dino.ander.movieapp.domain.state.ErrorHandler
import com.dino.ander.movieapp.domain.state.Result
import com.dino.ander.movieapp.room.dao.MovieDetailDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val movieDetailDao: MovieDetailDao,
    private val errorHandler: ErrorHandler
) : MovieRepository {
    override fun getPagedMovies(): Flow<PagingData<MovieEntity>> {
        return Pager(
            PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(apiService) }
        ).flow
    }

    override fun getMovieDetailFlow(
        movieId: Int
    ) = object : NetworkBoundResource<MovieDetailEntity, MovieDetails?>(errorHandler) {

        override suspend fun saveRemoteData(response: MovieDetailEntity) {
            movieDetailDao.saveMovie(response.toDB())
        }

        override fun fetchFromLocal() = movieDetailDao.getMovieDistinctUntilChanged(movieId).map {
            it?.toData()?.toDomain()
        }

        override suspend fun fetchFromRemote() = apiService.getMovieDetails(movieId)

        override fun shouldFetch(data: MovieDetails?) =
            (data == null || isMovieStale(data.modifiedAt))

    }.asFlow().flowOn(Dispatchers.IO)

    override fun loginFlow(user: String, pass: String): Flow<Result<Boolean>> {
        return flow {
            emit(Result.Loading())
            kotlinx.coroutines.delay(800)
            //val result = (user == "Admin" && pass == "Password*123.")
            val result = (user == "1" && pass == "1")
            if (result) {
                emit(Result.Success(true))
            } else {
                emit(Result.Error())
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun isMovieStale(lastUpdated: Long): Boolean {
        val oneDay = TimeUnit.DAYS.toMillis(1)
        return (System.currentTimeMillis() - oneDay) > lastUpdated
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 100
    }
}