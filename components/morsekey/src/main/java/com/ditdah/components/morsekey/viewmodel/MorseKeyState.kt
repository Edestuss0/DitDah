package com.ditdah.components.morsekey.viewmodel

data class MorseKeyState(
    val currentInput: String = "",
    val currentMorseInput: String = "",
    val isPressed: Boolean = false
)

sealed class MorseKeyEvents{
    data object Press : MorseKeyEvents()
    data object Release : MorseKeyEvents()
    data object Space : MorseKeyEvents()
    data object Delete : MorseKeyEvents()
}

sealed class MorseKeyEffects{
    data class ShowError(val message: String) : MorseKeyEffects()
}