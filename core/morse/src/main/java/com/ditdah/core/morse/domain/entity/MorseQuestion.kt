package com.ditdah.core.morse.domain.entity

data class MorseQuestion(
    val question: String,
    val answer: String,
    val type: MorseQuestionType
)

enum class MorseQuestionType {
    TEXT, AUDIO, MORSE
}
