package com.ditdah.features.freemode.play.viewmodel

import com.ditdah.core.morse.domain.entity.FreemodeDifficulty
import com.ditdah.core.morse.domain.entity.MorseQuestion

data class FreemodePlayState(
    val question: MorseQuestion.SimpleQuestion? = null,
    val answerStreak: Int = 0,
    val currentInput: String = "",
    val isLoading: Boolean = false,
    val isModalMenuOpen: Boolean = false,
    val answeredState: AnsweredState = AnsweredState(),
    val difficulty: FreemodeDifficulty = FreemodeDifficulty.MEDIUM
)

data class AnsweredState(
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false
)

sealed class FreemodePlayEvent {
    data object ChangeModalVisibility : FreemodePlayEvent()
    data class ChangeDifficulty(val difficulty: FreemodeDifficulty) : FreemodePlayEvent()
    data class Input(val input: String) : FreemodePlayEvent()
    data object Answer : FreemodePlayEvent()
    data object Continue : FreemodePlayEvent()
    data object Back : FreemodePlayEvent()
}

sealed class FreemodePlayEffect {
    data object Back : FreemodePlayEffect()
}