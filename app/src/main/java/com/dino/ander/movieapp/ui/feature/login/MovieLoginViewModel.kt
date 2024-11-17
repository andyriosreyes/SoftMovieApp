package com.dino.ander.movieapp.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dino.ander.movieapp.R
import com.dino.ander.movieapp.domain.state.Result
import com.dino.ander.movieapp.domain.usecase.login.GetLoginUseCase
import com.dino.ander.movieapp.ui.utils.UIStateLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieLoginViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase
) : ViewModel() {
    private val _loginFlow = MutableStateFlow<UIStateLogin>(UIStateLogin.Empty)
    val loginFlow: StateFlow<UIStateLogin> = _loginFlow.asStateFlow()

    fun login(user: String, pass: String) {
        viewModelScope.launch {
            getLoginUseCase.execute(user, pass).flowOn(Dispatchers.IO).collect { result ->
                _loginFlow.update {
                    when (result) {
                        is Result.Loading -> UIStateLogin.Loading
                        is Result.Success -> UIStateLogin.Success(result.data!!)
                        is Result.Error -> UIStateLogin.Error(R.string.login_error)
                    }
                }
            }
        }
    }

}