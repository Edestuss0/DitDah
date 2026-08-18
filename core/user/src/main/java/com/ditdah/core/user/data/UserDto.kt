package com.ditdah.core.user.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.ditdah.core.user.model.LoginInput
import com.ditdah.core.user.model.RegisterInput
import com.ditdah.core.user.model.SymbolStat
import com.ditdah.core.user.model.User
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

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
    lastPracticed = LocalDateTime.parse(last_practiced)
)

internal fun SymbolStat.toDto(): SymbolStatDto = SymbolStatDto(
    user_id = userId,
    symbol = symbol,
    correct = correct,
    wrong = wrong,
    consecutive_errors = consecutiveErrors,
    weight = weight,
    last_practiced = lastPracticed.toString()
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
    lastLogin = LocalDateTime.parse(lastLogin),
    invitedBy = invitedBy ?: 0,
    referralCode = referralCode ?: "",
    registeredDate = LocalDateTime.parse(registeredDate),
    friends = friends,
    unlockedAchievements = unlockedAchievements,
    symbolStats = symbolStats?.map { it.toEntity() } ?: emptyList()
)

internal fun User.toDto(): UserDto = UserDto(
    id = id,
    username = username,
    xp = xp,
    needXp = needXp,
    level = level,
    lessonsDoneEn = lessonDoneEn,
    lessonsDoneRu = lessonDoneRu,
    elo = elo,
    duelsWin = duelsWin,
    duelMaxScore = duelMaxScore,
    coins = coins,
    dayStreak = dayStreak,
    answerStreak = answerStreak,
    lastLogin = lastLogin.toString(),
    invitedBy = invitedBy,
    referralCode = referralCode,
    registeredDate = registeredDate.toString(),
    friends = friends,
    unlockedAchievements = unlockedAchievements,
    symbolStats = symbolStats.map { it.toDto() }
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