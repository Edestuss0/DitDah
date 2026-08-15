package com.ditdah.core.user.model

import java.time.LocalDateTime
import java.time.LocalTime

data class User(
    val id: Int,
    val username: String,
    val xp: Int,
    val needXp: Int,
    val level: Int,
    val lessonDoneEn: Int,
    val lessonDoneRu: Int,
    val elo: Int,
    val duelsWin: Int,
    val duelMaxScore: Int,
    val coins: Int,
    val dayStreak: Int,
    val answerStreak: Int,
    val lastLogin: LocalDateTime,
    val invitedBy: Int,
    val referralCode: String,
    val registeredDate: LocalDateTime,
    val friends: List<Int>,
    val unlockedAchievements: List<String>,
    val symbolStats: List<SymbolStat>
)