package com.dino.ander.movieapp.viewmodel

import com.dino.ander.movieapp.domain.model.MovieDetails
import com.dino.ander.movieapp.domain.usecase.movieDetail.GetMovieDetailUseCase
import com.dino.ander.movieapp.ui.feature.movieDetail.MovieDetailViewModel
import com.dino.ander.movieapp.ui.utils.UiState
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.any
import com.dino.ander.movieapp.domain.state.Result
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieDetailViewModelTest {

    @Mock
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun initSetUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        getMovieDetailUseCase = Mockito.mock(GetMovieDetailUseCase::class.java)
        movieDetailViewModel = MovieDetailViewModel(getMovieDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when i search for a movie it shows successful`(): Unit = runBlocking {
        launch(Dispatchers.IO) {
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
            Mockito.`when`(getMovieDetailUseCase.execute(any())).thenReturn(
                flow { emit(Result.Success(movieDetails)) }
            )
            movieDetailViewModel.getMovieDetail(1)
            delay(2000)

            assertTrue(movieDetailViewModel.movieFlow.value == UiState.Success(movieDetails))
            Mockito.verify(getMovieDetailUseCase).execute(1)
        }
    }

    @Test
    fun `when i search for a movie it shows loading`(): Unit = runBlocking {
        launch(Dispatchers.IO) {
            Mockito.`when`(getMovieDetailUseCase.execute(any())).thenReturn(
                flow { emit(Result.Loading()) }
            )
            movieDetailViewModel.getMovieDetail(1)

            assertTrue(movieDetailViewModel.movieFlow.value == UiState.Loading)
            Mockito.verify(getMovieDetailUseCase).execute(1)
        }
    }


}