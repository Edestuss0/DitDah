package com.ditdah.features.auth.viewmodel

import com.ditdah.core.user.domain.entity.LoginInput
import com.ditdah.core.user.domain.entity.RegisterInput

data class AuthState(
    val isLoading: Boolean = false,
    val isLogin: Boolean = true,
    val loginInput: LoginInput = LoginInput("", ""),
    val registerInput: RegisterInput = RegisterInput("", ""),
)

sealed class AuthEvent {
    sealed class LoginInput : AuthEvent() {
        data class InputUsername(val input: String) : AuthEvent.LoginInput()
        data class InputPassword(val input: String) : AuthEvent.LoginInput()
        data object Submit : AuthEvent.LoginInput()
    }

    sealed class RegisterInput : AuthEvent() {
        data class InputUsername(val input: String) : AuthEvent.RegisterInput()
        data class InputPassword(val input: String) : AuthEvent.RegisterInput()
        data object Submit : AuthEvent.RegisterInput()
    }

    data object ChangeMode : AuthEvent()
}

sealed class AuthEffect {
    data class Error(val message: String) : AuthEffect()
}