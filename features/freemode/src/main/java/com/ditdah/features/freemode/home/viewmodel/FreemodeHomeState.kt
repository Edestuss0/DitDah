package com.ditdah.features.freemode.home.viewmodel

import com.ditdah.core.morse.model.FreemodeDifficulty

data class FreemodeHomeState(
    val selectedDifficulty: FreemodeDifficulty = FreemodeDifficulty.MEDIUM,
)

sealed class FreemodeHomeEvent {
    data class SelectDifficulty(val difficulty: FreemodeDifficulty) : FreemodeHomeEvent()
    data object Play : FreemodeHomeEvent()
}

sealed class FreemodeHomeEffect {
    data class Play(val difficulty: FreemodeDifficulty) : FreemodeHomeEffect()
}