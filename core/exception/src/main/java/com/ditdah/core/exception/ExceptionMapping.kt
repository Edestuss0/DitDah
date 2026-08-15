package com.ditdah.core.exception

import io.ktor.client.network.sockets.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

fun Throwable.toAppException(): AppException {
    return when (this) {
        is AppException -> this
        is SocketTimeoutException -> AppException.Network.Timeout
        is UnknownHostException -> AppException.Network.NoInternet
        is CancellationException -> throw this
        is IllegalArgumentException -> throw this
        else -> AppException.Unknown
    }
}