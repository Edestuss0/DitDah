package com.ditdah.core.settings.domain.entity

data class Settings(
    val wpm: Int = 10,
    val language: Language = Language.EN,
    val daysToCache: Int = 3,
    val isDarkTheme: Boolean = false
) {
    fun getCacheDuration(): Long = (daysToCache * 24 * 60 * 60 * 1000).toLong()
    fun getDotDuration(): Long = (1200 / wpm).toLong()
    fun getDashDuration(): Long = getDotDuration() * 3
    fun getCharDuration(): Long = getDotDuration() * 3
    fun getWordDuration(): Long = getDotDuration() * 7
}

enum class Language(val label: String) {
    RU("Русский"), EN("Английский")
}
