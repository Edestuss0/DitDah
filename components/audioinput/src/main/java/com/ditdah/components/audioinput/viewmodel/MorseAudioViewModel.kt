package com.ditdah.components.audioinput.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditdah.core.morse.usecase.PlayMorseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MorseAudioViewModel @Inject constructor(
    private val player: PlayMorseUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MorseAudioState())
    val state = _state.asStateFlow()

    fun onEvent(event: MorseAudioEvent) {
        when (event) {
            is MorseAudioEvent.Input -> {
                _state.update { it.copy(
                    input = event.text
                ) }
            }
            is MorseAudioEvent.Play -> {
                if (state.value.isPlaying) return
                viewModelScope.launch {
                    _state.update { it.copy(isPlaying = true) }
                    player.playMorse(event.text)
                    _state.update { it.copy(isPlaying = false) }
                }
            }
        }
    }
}