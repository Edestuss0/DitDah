package com.ditdah.components.morsekey.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditdah.core.morse.domain.usecase.CodeMorseUseCase
import com.ditdah.core.morse.domain.usecase.PlayMorseUseCase
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MorseKeyViewModel @Inject constructor(
    private val getSettings: GetSettingsUseCase,
    private val decoder: CodeMorseUseCase,
    private val player: PlayMorseUseCase
) : ViewModel() {

    private val settings = getSettings()
    private var pressedTime = System.currentTimeMillis()
    private val _state = MutableStateFlow(MorseKeyState())
    val state = _state.asStateFlow()
    private val _effects = Channel<MorseKeyEffects>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()
    private var decodeJob: Job? = null

    fun onEvent(event: MorseKeyEvents) {
        when (event) {
            is MorseKeyEvents.Press -> onPress()
            is MorseKeyEvents.Release -> onRelease()
            is MorseKeyEvents.Space -> {
                _state.update { it.copy(currentInput = it.currentInput + " ") }
            }
            is MorseKeyEvents.Delete -> {
                _state.update { it.copy(currentInput = it.currentInput.dropLast(1)) }
            }
        }
    }

    private fun onPress() {
        decodeJob?.cancel()
        player.play()
        pressedTime = System.currentTimeMillis()
        _state.update { it.copy(isPressed = true) }
    }

    private fun onRelease() {
        val duration = System.currentTimeMillis() - pressedTime
        val isDash = duration >= settings.value.getDashDuration()
        val symbol = if (isDash) "–" else "·"

        player.stop()
        _state.update {
            it.copy(
                isPressed = false,
                currentMorseInput = it.currentMorseInput + symbol
            )
        }

        decodeJob = viewModelScope.launch {
            delay(settings.value.getCharDuration())

            val morse = state.value.currentMorseInput
            if (morse.isNotEmpty()) {
                val decoded = decoder.decode(morse)
                _state.update {
                    it.copy(
                        currentInput = it.currentInput + decoded,
                        currentMorseInput = ""
                    )
                }
            }
        }
    }
}