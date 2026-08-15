package com.ditdah.core.user.data.repository

import com.ditdah.core.di.ApplicationScope
import com.ditdah.core.exception.toAppException
import com.ditdah.core.user.data.local.UserLocalSource
import com.ditdah.core.user.data.remote.UserRemoteSource
import com.ditdah.core.user.model.LoginInput
import com.ditdah.core.user.model.RegisterInput
import com.ditdah.core.user.model.User
import com.ditdah.core.user.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteSource,
    private val local: UserLocalSource,
    @ApplicationScope private val scope: CoroutineScope
) : UserRepository {

    private val _userState = MutableStateFlow<Result<User>?>(null)
    private val userState = _userState.asStateFlow()

    init {
        scope.launch {
            while (true) {
                val result = runCatching { remote.getMe() }.mapCatching { it }.onFailure { it.toAppException() }

                _userState.value = result
                delay(10_000)
            }
        }
    }

    override suspend fun register(input: RegisterInput): Result<Unit> = runCatching { remote.register(input) }
        .onFailure { it.toAppException() }
        .onSuccess { local.changeAuthStatus(true) }

    override suspend fun login(input: LoginInput): Result<Unit> = runCatching { remote.login(input) }
        .onFailure { it.toAppException() }
        .onSuccess { local.changeAuthStatus(true) }

    override suspend fun getMe(): StateFlow<Result<User>?> = userState

    override suspend fun getUserById(id: Int): Result<User> = runCatching { remote.getUserById(id) }.onFailure { it.toAppException() }

    override suspend fun logout(): Result<Unit> = runCatching {
        local.changeAuthStatus(false)
        remote.logout()
    }

    override fun getAuthStatus(): Flow<Boolean> = local.getAuthStatus()
}