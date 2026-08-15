package com.ditdah.features.auth.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditdah.core.user.usecase.AuthUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthVIewModel @Inject constructor(
    private val auth: AuthUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _effects = Channel<AuthEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.LoginInput -> {
                when (event) {
                    is AuthEvent.LoginInput.InputPassword -> {
                        _state.update { it.copy(loginInput = it.loginInput.copy(password = event.input)) }
                    }
                    is AuthEvent.LoginInput.InputUsername -> {
                        _state.update { it.copy(loginInput = it.loginInput.copy(username = event.input)) }

                    }
                    is AuthEvent.LoginInput.Submit -> {
                        viewModelScope.launch {
                            runCatching { auth.login(state.value.loginInput) }.onFailure {
                                _effects.send(AuthEffect.Error(it.message ?: "Произошла непредвиденная ошибка"))
                            }
                        }
                    }
                }
            }
            is AuthEvent.RegisterInput -> {
                when (event) {
                    is AuthEvent.RegisterInput.InputPassword -> {
                        _state.update { it.copy(registerInput = it.registerInput.copy(password = event.input)) }
                    }
                    is AuthEvent.RegisterInput.InputUsername -> {
                        _state.update { it.copy(registerInput = it.registerInput.copy(username = event.input)) }

                    }
                    is AuthEvent.RegisterInput.Submit -> {
                        viewModelScope.launch {
                            runCatching { auth.register(state.value.registerInput) }.onFailure {
                                _effects.send(AuthEffect.Error(it.message ?: "Произошла непредвиденная ошибка"))
                            }
                        }
                    }
                }
            }
            is AuthEvent.ChangeMode -> {
                _state.update { it.copy(isLogin = !it.isLogin) }
            }
        }
    }
}