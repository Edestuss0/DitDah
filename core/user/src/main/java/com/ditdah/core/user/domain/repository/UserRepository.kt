package com.ditdah.core.user.domain.repository

import com.ditdah.core.user.domain.entity.LoginInput
import com.ditdah.core.user.domain.entity.RegisterInput
import com.ditdah.core.user.domain.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface UserRepository {
    suspend fun register(input: RegisterInput): Result<Unit>
    suspend fun login(input: LoginInput): Result<Unit>
    suspend fun getMe(): StateFlow<User?>
    suspend fun getUserById(id: Int): Result<User>
    suspend fun logout(): Result<Unit>
    fun getAuthStatus(): Flow<Boolean>
    suspend fun invalidateMe()
}