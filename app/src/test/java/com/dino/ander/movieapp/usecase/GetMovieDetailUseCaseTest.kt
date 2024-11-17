package com.dino.ander.movieapp.usecase

import com.dino.ander.movieapp.domain.model.MovieDetails
import com.dino.ander.movieapp.domain.repository.MovieRepository
import com.dino.ander.movieapp.domain.state.Result
import com.dino.ander.movieapp.domain.usecase.movieDetail.GetMovieDetailUseCase
import junit.framework.TestCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetMovieDetailUseCaseTest {

    @Mock
    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase
    private lateinit var mockMovieDetail: Flow<com.dino.ander.movieapp.domain.state.Result<MovieDetails?>>

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun initSetUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        movieRepository = Mockito.mock(MovieRepository::class.java)
        getMovieDetailUseCase = GetMovieDetailUseCase(movieRepository)
        mockMovieDetail = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when i search for a movie it shows successful`(): Unit = runTest {
        val movieDetails: MovieDetails? = MovieDetails(
            id = 1,
            overview = "pelicula",
            posterPath = "www.google.com",
            releaseDate = "",
            title = "goku",
            popularity = 0.0,
            voteAverage = 0.0,
            voteCount = 2,
            status = "active",
            modifiedAt = 12
        )
        Mockito.`when`(movieRepository.getMovieDetailFlow(any())).thenReturn(
            flow { emit(Result.Success(movieDetails)) }
        )
        getMovieDetailUseCase.execute(1).collect {
            TestCase.assertTrue(movieDetails == it.data)
        }

    }


}