package com.ditdah.core.user.repository

import com.ditdah.core.user.model.LoginInput
import com.ditdah.core.user.model.RegisterInput
import com.ditdah.core.user.model.User
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