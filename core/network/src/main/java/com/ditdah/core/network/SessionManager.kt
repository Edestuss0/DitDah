package com.ditdah.core.network

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val cookiesStorage: PersistentCookiesStorage
) {
    private val _logoutEvent = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val logoutEvent = _logoutEvent.asSharedFlow()

    suspend fun forceLogout() {
        cookiesStorage.clearAll()
        _logoutEvent.tryEmit(Unit)
        Log.d("LOGOUT", "true")
    }
}