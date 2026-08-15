package com.ditdah.features.freemode.play.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditdah.core.morse.model.FreemodeDifficulty
import com.ditdah.core.morse.usecase.FreemodeGeneratorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FreemodePlayViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val getQuestion: FreemodeGeneratorUseCase
): ViewModel() {

    private val _state = MutableStateFlow(FreemodePlayState())
    val state = _state.asStateFlow()

    private val _effects = Channel<FreemodePlayEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        getQuestion()
    }

    fun onEvent(event: FreemodePlayEvent) {
        when (event) {
            FreemodePlayEvent.ChangeModalVisibility -> {
                _state.update { it.copy(isModalMenuOpen = !it.isModalMenuOpen) }
            }
            is FreemodePlayEvent.Input -> {
                _state.update { it.copy(currentInput = event.input) }
            }
            is FreemodePlayEvent.ChangeDifficulty -> {
                if (event.difficulty != state.value.difficulty) {
                    _state.update { it.copy(difficulty = event.difficulty) }
                    getQuestion()
                }
            }
            is FreemodePlayEvent.Back -> {
                viewModelScope.launch {
                    _effects.send(FreemodePlayEffect.Back)
                }
            }
            is FreemodePlayEvent.Answer -> {
                if (state.value.currentInput.uppercase().trim() == state.value.question?.answer?.uppercase()?.trim()) {
                    _state.update { it.copy(answeredState = it.answeredState.copy(isAnswered = true, isCorrect = true), answerStreak = it.answerStreak + 1, currentInput = "") }
                } else {
                    _state.update { it.copy(answeredState = it.answeredState.copy(isAnswered = true, isCorrect = false), answerStreak = 0, currentInput = "") }
                }
            }
            is FreemodePlayEvent.Continue -> {
                _state.update { it.copy(answeredState = it.answeredState.copy(isAnswered = false), currentInput = "", question = null) }
                getQuestion()
            }
        }
    }

    private fun getQuestion() {
        _state.update { it.copy(isLoading = true) }
        val question = getQuestion(state.value.difficulty)
        _state.update { it.copy(question = question, isLoading = false) }
    }
}