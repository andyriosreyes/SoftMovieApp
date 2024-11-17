package com.dino.ander.movieapp.usecase

import com.dino.ander.movieapp.domain.repository.MovieRepository
import com.dino.ander.movieapp.domain.state.Result
import com.dino.ander.movieapp.domain.usecase.login.GetLoginUseCase
import junit.framework.TestCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetLoginUseCaseTest {

    @Mock
    private lateinit var getLoginUseCase: GetLoginUseCase
    private lateinit var movieRepository: MovieRepository
    private lateinit var response: Flow<Result<Boolean>>

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun initSetUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        movieRepository = Mockito.mock(MovieRepository::class.java)
        getLoginUseCase = GetLoginUseCase(movieRepository)
        response = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when i search for a movie it shows successful`() = runTest {
        Mockito.`when`(movieRepository.loginFlow(any(), any())).thenReturn(
            flow { emit(Result.Success(true)) }
        )
        val result = getLoginUseCase.execute("prueba", "prueba")

        val res = result.first().data

        TestCase.assertTrue(res == true)
    }

}