package com.ditdah.core.user.domain.entity

import java.time.LocalDateTime

data class SymbolStat(
    val userId: Int,
    val symbol: String,
    val correct: Int,
    val wrong: Int,
    val consecutiveErrors: Int,
    val weight: Int,
    val lastPracticed: LocalDateTime
)
