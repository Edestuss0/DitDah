package com.ditdah.core.user.model

import java.time.LocalDateTime
import java.time.LocalTime

data class SymbolStat(
    val userId: Int,
    val symbol: String,
    val correct: Int,
    val wrong: Int,
    val consecutiveErrors: Int,
    val weight: Int,
    val lastPracticed: LocalDateTime
)
