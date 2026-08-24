package com.ditdah.core.user.domain.entity

import java.time.LocalDateTime

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