package com.ditdah.core.user.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.ditdah.core.di.ApplicationScope
import com.ditdah.core.exception.AppException
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Singleton
internal class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteSource,
    private val local: UserLocalSource,
    @ApplicationScope private val scope: CoroutineScope
) : UserRepository {

    private var _userState = local.observeMe()
    private val userState = _userState.onEach { user ->
            if (user == null) invalidateMe()
        }.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun invalidateMe() {
        val user = runCatching { remote.getMe() }.mapCatching { it }.onFailure { it.toAppException() }
        val userData = user.getOrNull()
        if (user.isSuccess && userData != null) {
            local.invalidateMe()
            local.insert(true, userData)
        } else {
            throw user.exceptionOrNull() ?: AppException.Unknown
        }
    }

    override suspend fun register(input: RegisterInput): Result<Unit> = runCatching { remote.register(input) }
        .onFailure { it.toAppException() }
        .onSuccess { local.changeAuthStatus(true) }

    override suspend fun login(input: LoginInput): Result<Unit> = runCatching { remote.login(input) }
        .onFailure { it.toAppException() }
        .onSuccess { local.changeAuthStatus(true) }

    override suspend fun getMe(): StateFlow<User?> = userState

    override suspend fun getUserById(id: Int): Result<User> = runCatching { remote.getUserById(id) }.onFailure { it.toAppException() }

    override suspend fun logout(): Result<Unit> = runCatching {
        local.changeAuthStatus(false)
        remote.logout()
    }

    override fun getAuthStatus(): Flow<Boolean> = local.getAuthStatus()
}