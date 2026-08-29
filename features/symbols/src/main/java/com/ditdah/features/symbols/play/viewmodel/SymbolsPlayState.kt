package com.ditdah.features.symbols.play.viewmodel

import com.ditdah.core.morse.domain.entity.MorseQuestion

data class SymbolsPlayState(
    val question: MorseQuestion.SimpleQuestion? = null,
    val answerStreak: Int = 0,
    val currentInput: String = "",
    val isLoading: Boolean = false,
    val answeredState: AnsweredState = AnsweredState(),
)

data class AnsweredState(
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false
)

sealed class SymbolsPlayEvent {
    data class Input(val input: String) : SymbolsPlayEvent()
    data object Answer : SymbolsPlayEvent()
    data object Continue : SymbolsPlayEvent()
    data object Back : SymbolsPlayEvent()
    data object Retry : SymbolsPlayEvent()
}

sealed class SymbolsPlayEffect {
    data object Back : SymbolsPlayEffect()
    data class Error(val message: String) : SymbolsPlayEffect()
}
