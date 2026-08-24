package com.ditdah.core.user.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.ditdah.core.config.API_URL
import com.ditdah.core.exception.AppException
import com.ditdah.core.user.data.UserDto
import com.ditdah.core.user.data.toDto
import com.ditdah.core.user.data.toEntity
import com.ditdah.core.user.domain.entity.LoginInput
import com.ditdah.core.user.domain.entity.RegisterInput
import com.ditdah.core.user.domain.entity.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject

internal class UserRemoteSource @Inject constructor(
    private val client: HttpClient
) {
    suspend fun login(input: LoginInput) {
        val response = client.post("$API_URL/login") {
            setBody(input.toDto())
            contentType(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw AppException.Network.ServerError(response.status.value)
        }
    }

    suspend fun register(input: RegisterInput) {
        val response = client.post("$API_URL/register") {
            setBody(input.toDto())
            contentType(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw AppException.Network.ServerError(response.status.value)
        }
    }

    suspend fun logout() {
        val response = client.post("$API_URL/api/logout")
        if (!response.status.isSuccess()) {
            throw AppException.Network.ServerError(response.status.value)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getMe(): User {
        val response = client.get("$API_URL/api/me")
        if (!response.status.isSuccess()) {
            throw AppException.Network.ServerError(response.status.value)
        }
        try {
            val parsed = response.body<UserDto>()
            return parsed.toEntity()
        } catch (_: Exception) {
            throw AppException.Serialization
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUserById(id: Int): User {
        val response = client.get("$API_URL/api/user/$id")
        if (!response.status.isSuccess()) {
            throw AppException.Network.ServerError(response.status.value)
        }
        try {
            val parsed = response.body<UserDto>()
            return parsed.toEntity()
        } catch (_: Exception) {
            throw AppException.Serialization
        }
    }
}