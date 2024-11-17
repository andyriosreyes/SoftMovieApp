package com.dino.ander.movieapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dino.ander.movieapp.data.model.MovieEntity
import com.dino.ander.movieapp.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource (
    private val apiService: ApiService
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieEntity> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getMovies(
                page = page,
            ).body() ?: run { return LoadResult.Error(Throwable()) }

            LoadResult.Page(
                data = response.results,
                prevKey = if(page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}