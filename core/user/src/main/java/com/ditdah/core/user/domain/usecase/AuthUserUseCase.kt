package com.ditdah.core.user.domain.usecase

import com.ditdah.core.user.domain.entity.LoginInput
import com.ditdah.core.user.domain.entity.RegisterInput
import com.ditdah.core.user.domain.repository.UserRepository
import javax.inject.Inject

class AuthUserUseCase @Inject internal constructor(
    private val repository: UserRepository
) {
    suspend fun login(input: LoginInput) {
        when {
            input.username.isBlank()-> {
                throw IllegalArgumentException("Пожалуйста, введите имя пользователя")
            }
            input.password.isBlank()-> {
                throw IllegalArgumentException("Пожалуйста, введите пароль")
            }
        }
        repository.login(input)
    }
    suspend fun register(input: RegisterInput) {
        when {
            input.username.length < 6 || input.username.length > 20 -> {
                throw IllegalArgumentException("Имя пользователя должно содержать от 6 до 20 символов")
            }
            input.password.length < 4  -> {
                throw IllegalArgumentException(" должно содержать от 4 символов")
            }
        }
        repository.register(input)
    }
    suspend fun logout() = repository.logout()
    fun getStatus() = repository.getAuthStatus()
}