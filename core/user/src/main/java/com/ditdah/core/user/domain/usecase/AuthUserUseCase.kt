package com.ditdah.core.user.domain.usecase

import com.ditdah.core.exception.AppException
import com.ditdah.core.user.domain.entity.LoginInput
import com.ditdah.core.user.domain.entity.RegisterInput
import com.ditdah.core.user.domain.repository.UserRepository
import javax.inject.Inject

class AuthUserUseCase @Inject internal constructor(
    private val repository: UserRepository
) {
    suspend fun login(input: LoginInput): Result<Unit> {
        when {
            input.username.isBlank()-> {
                throw AppException.Validation("Пожалуйста, введите имя пользователя")
            }
            input.password.isBlank()-> {
                throw AppException.Validation("Пожалуйста, введите пароль")
            }
        }
        return repository.login(input)
    }

    suspend fun register(input: RegisterInput): Result<Unit> {
        when {
            input.username.length < 6 || input.username.length > 20 -> {
                throw AppException.Validation("Имя пользователя должно содержать от 6 до 20 символов")
            }
            input.password.length < 4  -> {
                throw AppException.Validation("Пароль должен содержать от 4 символов")
            }
        }
        return repository.register(input)
    }
    suspend fun logout() = repository.logout()
    fun getStatus() = repository.getAuthStatus()
}