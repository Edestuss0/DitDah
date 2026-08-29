package com.ditdah.core.user.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.ditdah.core.di.ApplicationScope
import com.ditdah.core.exception.toAppException
import com.ditdah.core.user.data.local.UserLocalSource
import com.ditdah.core.user.data.remote.UserRemoteSource
import com.ditdah.core.user.domain.entity.LoginInput
import com.ditdah.core.user.domain.entity.RegisterInput
import com.ditdah.core.user.domain.entity.User
import com.ditdah.core.user.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Singleton
internal class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteSource,
    private val local: UserLocalSource,
    @ApplicationScope private val scope: CoroutineScope
) : UserRepository {

    private val _userState = local.observeMe()
    private val userState = _userState.onEach { user -> if (user == null) invalidateMe() }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    override suspend fun invalidateMe() {
        runCatching { remote.getMe() }.onSuccess { local.invalidateMe(); local.insert(true, it) }.getOrElse { throw it.toAppException() }
    }

    override suspend fun register(input: RegisterInput): Result<Unit> =
        runCatching { remote.register(input) }.onSuccess { local.changeAuthStatus(true) }
            .recoverCatching { throw it.toAppException() }

    override suspend fun login(input: LoginInput): Result<Unit> =
        runCatching { remote.login(input) }.onSuccess { local.changeAuthStatus(true) }
            .recoverCatching { throw it.toAppException() }

    override suspend fun getMe(): StateFlow<User?> = userState

    override suspend fun getUserById(id: Int): Result<User> = runCatching { remote.getUserById(id) }.recoverCatching { throw it.toAppException() }

    override suspend fun logout(): Result<Unit> = runCatching { local.changeAuthStatus(false); remote.logout() }

    override fun getAuthStatus(): Flow<Boolean> = local.getAuthStatus()
}