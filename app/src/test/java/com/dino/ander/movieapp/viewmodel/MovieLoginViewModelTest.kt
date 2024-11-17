package com.dino.ander.movieapp.viewmodel

import com.dino.ander.movieapp.R
import com.dino.ander.movieapp.domain.state.Result
import com.dino.ander.movieapp.domain.usecase.login.GetLoginUseCase
import com.dino.ander.movieapp.ui.feature.login.MovieLoginViewModel
import com.dino.ander.movieapp.ui.utils.UIStateLogin
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieLoginViewModelTest {

    @Mock
    private lateinit var movieLoginViewModel: MovieLoginViewModel
    private lateinit var getLoginUseCase: GetLoginUseCase

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun initSetUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        getLoginUseCase = Mockito.mock(GetLoginUseCase::class.java)
        movieLoginViewModel = MovieLoginViewModel(getLoginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when i search for a movie it shows loading`(): Unit = runBlocking {
        val user = "1"
        val pass = "4"
        Mockito.`when`(getLoginUseCase.execute(any(), any())).thenReturn(
            flow { emit(Result.Loading()) }
        )
        movieLoginViewModel.login(user, pass)
        delay(2000)

        assertTrue(/* condition = */ movieLoginViewModel.loginFlow.value == UIStateLogin.Loading)
        Mockito.verify(getLoginUseCase).execute(user,pass)
    }

    @Test
    fun `when i login for a movie it shows successful`(): Unit = runBlocking {
        val user = "1"
        val pass = "1"
        Mockito.`when`(getLoginUseCase.execute(any(), any())).thenReturn(
            flow { emit(Result.Success(true)) }
        )
        movieLoginViewModel.login(user, pass)
        delay(2000)

        assertTrue(/* condition = */ movieLoginViewModel.loginFlow.value == UIStateLogin.Success(
            true
        )
        )
    }

    @Test
    fun `when i search for a movie it shows error`(): Unit = runBlocking {
        val user = "1"
        val pass = "1"
        Mockito.`when`(getLoginUseCase.execute(any(), any())).thenReturn(
            flow { emit(Result.Error()) }
        )
        movieLoginViewModel.login(user, pass)
        delay(2000)

        assertTrue(/* condition = */ movieLoginViewModel.loginFlow.value == UIStateLogin.Error(R.string.login_error))
    }
}