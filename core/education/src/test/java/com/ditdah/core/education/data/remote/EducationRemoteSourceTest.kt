package com.ditdah.core.education.data.remote

import com.ditdah.core.education.domain.entity.LessonTaskType
import com.ditdah.core.exception.AppException
import com.ditdah.core.settings.domain.entity.Language
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.mockk.clearAllMocks
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Test

class EducationRemoteSourceTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var source: EducationRemoteSource

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `generate serverError`() = runTest(testDispatcher) {
        val mockEngine = MockEngine {
            respond(
                content = """{"error": "no such language"}""",
                status = HttpStatusCode.NotFound,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }
        source = EducationRemoteSource(
            client = HttpClient(mockEngine) {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
            }
        )
        val result = runCatching { source.generate(Language.EN) }

        assertEquals(result.getOrNull(), null)
        assertEquals(result.exceptionOrNull(), AppException.Network.ServerError(404))
    }

    @Test
    fun `generate serialization Error`() = runTest(testDispatcher) {
        val mockEngine = MockEngine {
            respond(
                content = """{"id": 4, "title": 24, "tasks": []}""",
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }
        source = EducationRemoteSource(
            client = HttpClient(mockEngine) {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
            }
        )
        val result = runCatching { source.generate(Language.EN) }

        assertEquals(result.getOrNull(), null)
        assertEquals(result.exceptionOrNull(), AppException.Serialization)
    }

    @Test
    fun `generate ok`() = runTest(testDispatcher) {
        val mockEngine = MockEngine {
            respond(
                content = """{
                    "id": 3,
                    "userId": 10,
                    "order": 1,
                    "title": "Lesson 1",
                    "description": "Learn new Morse symbols!",
                    "xpReward": 15,
                    "language": "en",
                    "status": "in_progress",
                    "score": 0,
                    "createdAt": "2026-08-29T17:36:05.4400151+03:00",
                    "tasks": [
                        {
                            "id": 0,
                            "lessonId": 0,
                            "order": 3,
                            "type": "quiz",
                            "payload": {
                                "question": "Как звучит буква C?",
                                "options": [
                                    "-.-",
                                    "-.-."
                                ],
                                "correctIndex": 1
                            }
                        },
                        {
                            "id": 0,
                            "lessonId": 0,
                            "order": 4,
                            "type": "tap",
                            "payload": {
                                "question": "Отстучите букву A",
                                "answer": ".-"
                            }
                        }   
                    ]
                }""",
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }
        source = EducationRemoteSource(
            client = HttpClient(mockEngine) {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
            }
        )
        val result = runCatching { source.generate(Language.EN) }

        assertEquals(result.getOrNull()?.id, 3)
        assertEquals(result.getOrNull()?.tasks[1]?.type, LessonTaskType.TAP)
        assertEquals(result.exceptionOrNull(), null)
    }
}