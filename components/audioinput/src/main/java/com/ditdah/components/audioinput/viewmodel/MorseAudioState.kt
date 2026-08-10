package com.ditdah.components.audioinput.viewmodel

data class MorseAudioState(
    val input: String = "",
    val isPlaying: Boolean = false
)

sealed class MorseAudioEvent {
    data class Play(val text: String) : MorseAudioEvent()
    data class Input(val text: String) : MorseAudioEvent()
}