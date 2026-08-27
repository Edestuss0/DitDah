package com.ditdah.features.symbols.play.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditdah.core.morse.domain.usecase.FreemodeGeneratorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SymbolsPlayViewModel @Inject internal constructor(
    private val generator: FreemodeGeneratorUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(SymbolsPlayState())
    val state = _state.asStateFlow()

    private val _effects = Channel<SymbolsPlayEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    val symbol = savedStateHandle.get<String>("symbol") ?: ""

    init {
        getQuestion()
    }

    fun onEvent(event: SymbolsPlayEvent) {
        when (event) {
            is SymbolsPlayEvent.Input -> {
                _state.update { it.copy(currentInput = event.input) }
            }
            is SymbolsPlayEvent.Back -> {
                viewModelScope.launch {
                    _effects.send(SymbolsPlayEffect.Back)
                }
            }
            is SymbolsPlayEvent.Answer -> {
                if (state.value.currentInput.uppercase().trim() == state.value.question?.answer?.uppercase()?.trim()) {
                    _state.update { it.copy(answeredState = it.answeredState.copy(isAnswered = true, isCorrect = true), answerStreak = it.answerStreak + 1, currentInput = "") }
                } else {
                    _state.update { it.copy(answeredState = it.answeredState.copy(isAnswered = true, isCorrect = false), answerStreak = 0, currentInput = "") }
                }
            }
            is SymbolsPlayEvent.Continue -> {
                _state.update { it.copy(answeredState = it.answeredState.copy(isAnswered = false), currentInput = "", question = null) }
                getQuestion()
            }
            is SymbolsPlayEvent.Retry -> {
                getQuestion()
            }
        }
    }

    private fun getQuestion() {
        _state.update { it.copy(isLoading = true) }
        val question = runCatching { generator(symbol = symbol) }
        if (question.isSuccess && question.getOrNull() != null) {
            _state.update { it.copy(question = question.getOrNull(), isLoading = false) }
        } else {
            _state.update { it.copy(question = null, isLoading = false) }
            viewModelScope.launch {
                _effects.send(SymbolsPlayEffect.Error(question.exceptionOrNull()?.message ?: "Ошибка при попытке получения вопроса"))
            }
        }
    }
}