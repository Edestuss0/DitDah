package com.ditdah.core.education.data.remote

import com.ditdah.core.config.API_URL
import com.ditdah.core.education.data.LessonDto
import com.ditdah.core.education.data.toEntity
import com.ditdah.core.education.domain.entity.Lesson
import com.ditdah.core.exception.AppException
import com.ditdah.core.settings.domain.entity.Language
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject

internal class EducationRemoteSource @Inject constructor(
    private val client: HttpClient
) {
    suspend fun generate(language: Language): Lesson {
        val response = client.post("$API_URL/api/lessons/generate/${language.name.lowercase()}") {
            contentType(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw AppException.Network.ServerError(response.status.value)
        val dto = runCatching { response.body<LessonDto>() }.getOrNull()
            ?: throw AppException.Serialization
        return dto.toEntity()
    }
}