package com.ditdah.features.freemode.play.viewmodel

import com.ditdah.core.morse.model.MorseQuestion

data class FreemodePlayState(
    val question: MorseQuestion? = null,
    val answerStreak: Int = 0,
    val currentInput: String = "",
    val isLoading: Boolean = false,
    val answeredState: AnsweredState = AnsweredState()
)

data class AnsweredState(
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false
)

sealed class FreemodePlayEvent {
    data class Input(val input: String) : FreemodePlayEvent()
    data object Answer : FreemodePlayEvent()
    data object Continue : FreemodePlayEvent()
    data object Back : FreemodePlayEvent()
}

sealed class FreemodePlayEffect {
    data object Back : FreemodePlayEffect()
}