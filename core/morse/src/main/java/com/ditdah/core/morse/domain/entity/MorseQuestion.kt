package com.ditdah.core.morse.domain.entity

sealed class MorseQuestion {
    data class SimpleQuestion(
        val question: String,
        val answer: String,
        val type: MorseQuestionType
    ) : MorseQuestion()

    data class QuizQuestion(
        val question: String,
        val options: List<String>,
        val correctIndex: Int
    ) : MorseQuestion()
}


enum class MorseQuestionType {
    TEXT, AUDIO, MORSE
}
