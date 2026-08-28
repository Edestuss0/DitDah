package com.ditdah.core.user.data.repository

import com.ditdah.core.exception.AppException
import com.ditdah.core.user.data.local.UserLocalSource
import com.ditdah.core.user.data.remote.UserRemoteSource
import com.ditdah.core.user.domain.entity.User
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    private lateinit var repository: UserRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var remote: UserRemoteSource
    private lateinit var local: UserLocalSource

    @Before
    fun setUp() {
        remote = mockk(relaxed = true)
        local = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private val userMock = User(
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
        lastLogin = LocalDateTime.parse("2026-08-15T16:23:54.853937"),
        registeredDate = LocalDateTime.parse("2026-08-15T16:23:54.853937"),
        referralCode = "#GK#5f",
        invitedBy = 5,
        friends = emptyList(),
        duelMaxScore = 41,
        lessonDoneEn = 1,
        lessonDoneRu = 14
    )

    @Test
    fun `getMe ok`() = runTest(testDispatcher) {


        every { local.observeMe() } returns flowOf(userMock)
        repository = UserRepositoryImpl(scope = backgroundScope, local = local, remote = remote)
        val result = repository.getMe().first { it != null }

        assertEquals(result, userMock)
    }

    @Test
    fun `getMe null`() = runTest(testDispatcher) {
        every { local.observeMe() } returns flowOf(null)
        repository = UserRepositoryImpl(scope = backgroundScope, local = local, remote = remote)
        val result = repository.getMe()

        testScheduler.advanceUntilIdle()

        assertEquals(result.value, null)
    }

    @Test
    fun `invalidate me ok`() = runTest(testDispatcher) {

        var isInserted = false
        coEvery { remote.getMe() } returns userMock
        coEvery { local.insert(true, userMock) } coAnswers {
            isInserted = true
        }

        repository = UserRepositoryImpl(scope = backgroundScope, local = local, remote = remote)
        repository.invalidateMe()

        assertEquals(isInserted, true)
    }

    @Test
    fun `invalidate me error`() = runTest(testDispatcher) {

        coEvery { remote.getMe() } throws AppException.Network.ServerError(500)

        repository = UserRepositoryImpl(scope = backgroundScope, local = local, remote = remote)
        val result = runCatching { repository.invalidateMe() }

        assertEquals(result.exceptionOrNull(), AppException.Network.ServerError(500))
        assertEquals(result.getOrNull(), null)
    }

}