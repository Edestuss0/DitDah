package com.ditdah.core.user.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.ditdah.core.user.model.LoginInput
import com.ditdah.core.user.model.RegisterInput
import com.ditdah.core.user.model.SymbolStat
import com.ditdah.core.user.model.User
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.LocalTime.parse
import java.time.OffsetDateTime
import kotlin.time.Instant

@Serializable
internal data class SymbolStatDto(
    val user_id: Int,
    val symbol: String,
    val correct: Int,
    val wrong: Int,
    val consecutive_errors: Int,
    val weight: Int,
    val last_practiced: String
)

@RequiresApi(Build.VERSION_CODES.O)
internal fun SymbolStatDto.toEntity(): SymbolStat = SymbolStat(
    userId = user_id,
    symbol = symbol,
    correct = correct,
    wrong = wrong,
    consecutiveErrors = consecutive_errors,
    weight = weight,
    lastPracticed = OffsetDateTime.parse(last_practiced).toLocalDateTime()
)

@Serializable
internal data class UserDto(
    val id: Int,
    val username: String,
    val xp: Int,
    val needXp: Int,
    val level: Int,
    val lessonsDoneEn: Int,
    val lessonsDoneRu: Int,
    val elo: Int,
    val duelsWin: Int,
    val duelMaxScore: Int,
    val coins: Int,
    val dayStreak: Int,
    val answerStreak: Int,
    val lastLogin: String,
    val invitedBy: Int?,
    val referralCode: String?,
    val registeredDate: String,
    val friends: List<Int>,
    val unlockedAchievements: List<String>,
    val symbolStats: List<SymbolStatDto>?
)

@RequiresApi(Build.VERSION_CODES.O)
internal fun UserDto.toEntity(): User = User(
    id = id,
    username = username,
    xp = xp,
    needXp = needXp,
    level = level,
    lessonDoneEn = lessonsDoneEn,
    lessonDoneRu = lessonsDoneRu,
    elo = elo,
    duelsWin = duelsWin,
    duelMaxScore = duelMaxScore,
    coins = coins,
    dayStreak = dayStreak,
    answerStreak = answerStreak,
    lastLogin = OffsetDateTime.parse(lastLogin).toLocalDateTime(),
    invitedBy = invitedBy ?: 0,
    referralCode = referralCode ?: "",
    registeredDate = OffsetDateTime.parse(registeredDate).toLocalDateTime(),
    friends = friends,
    unlockedAchievements = unlockedAchievements,
    symbolStats = symbolStats?.map { it.toEntity() } ?: emptyList()
)

@Serializable
internal data class RegisterInputDto(
    val username: String,
    val password: String
)

internal fun RegisterInput.toDto(): RegisterInputDto = RegisterInputDto(username = username, password = password)

@Serializable
internal data class LoginInputDto(
    val username: String,
    val password: String
)

internal fun LoginInput.toDto(): LoginInputDto = LoginInputDto(username = username, password = password)