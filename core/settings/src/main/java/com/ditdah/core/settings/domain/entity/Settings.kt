package com.ditdah.core.settings.domain.entity

data class Settings(
    val wpm: Int = 10,
    val language: Language = Language.EN
) {
    fun getDotDuration(): Long = (1200 / wpm).toLong()
    fun getWordDuration(): Long = getDotDuration() * 3
}

enum class Language{
    RU, EN
}
