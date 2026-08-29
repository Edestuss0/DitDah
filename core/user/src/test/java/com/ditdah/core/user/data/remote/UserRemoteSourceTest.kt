package com.ditdah.core.user.data.remote

import com.ditdah.core.exception.AppException
import com.ditdah.core.user.data.UserDto
import com.ditdah.core.user.domain.entity.LoginInput
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

class UserRemoteSourceTest {
    private lateinit var source: UserRemoteSource
    private val testDispatcher = StandardTestDispatcher()

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getMe full success`() = runTest(testDispatcher) {

        val content = UserDto(
            id = 4,
            username = "TRAKTARISTKA",
            xp = 423,
            level = 13,
            answerStreak = 12,
            dayStreak = 32,
            symbolStats = emptyList(),
            elo = 1514,
            coins = 141,
            unlockedAchievements = emptyList(),
            needXp = 512,
            duelsWin = 87,
            lastLogin = "2026-08-15T16:23:54.853937",
            registeredDate = "2026-08-15T16:23:54.853937",
            referralCode = "#GK#5f",
            invitedBy = 5,
            friends = emptyList(),
            duelMaxScore = 41,
            lessonsDoneEn = 1,
            lessonsDoneRu = 14
        )

        val mockEngine = MockEngine { _ ->
            respond(
                content = Json.encodeToString(content),
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        source = UserRemoteSource(httpClient)

        val result = source.getMe()

        assertEquals(result.id, 4)
        assertEquals(result.username, "TRAKTARISTKA")
    }

    @Test
    fun `getMe 404 status`() = runTest(testDispatcher) {

        val mockEngine = MockEngine { _ ->
            respond(
                content = """{"error": "no such methor"}""",
                status = HttpStatusCode.NotFound,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        source = UserRemoteSource(httpClient)

        val result = runCatching { source.getMe() }

        assertEquals(result.getOrNull(), null)
        assertEquals(result.exceptionOrNull(), AppException.Network.ServerError(404))
    }

    @Test
    fun `getMe serialization error`() = runTest(testDispatcher) {

        val mockEngine = MockEngine { _ ->
            respond(
                content = """{"username": "TRAKTARISTKA", "id": 53, "level": 12}""",
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        source = UserRemoteSource(httpClient)

        val result = runCatching { source.getMe() }

        assertEquals(result.getOrNull(), null)
        assertEquals(result.exceptionOrNull(), AppException.Serialization)
    }

    @Test
    fun `login ok`() = runTest(testDispatcher) {
        val mockEngine = MockEngine { _ ->
            respond(
                content = """{"message": "login successful"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        source = UserRemoteSource(httpClient)

        val result =
            runCatching { source.login(LoginInput(username = "sdds", password = "traktaristka67")) }

        assertEquals(result.isSuccess, true)
        assertEquals(result.exceptionOrNull(), null)
    }

    @Test
    fun `login invalid password`() = runTest(testDispatcher) {
        val mockEngine = MockEngine { _ ->
            respond(
                content = """{"error": "invalid login or password"}""",
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        source = UserRemoteSource(httpClient)

        val result =
            runCatching { source.login(LoginInput(username = "sdds", password = "traktaristka67")) }

        assertEquals(result.isSuccess, false)
        assert(result.exceptionOrNull() is IllegalArgumentException)
    }
}