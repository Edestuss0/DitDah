package com.ditdah.features.freemode.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FreemodeHomeViewModel @Inject internal constructor(): ViewModel() {

    private val _state = MutableStateFlow(FreemodeHomeState())
    val state = _state.asStateFlow()

    private val _effects = Channel<FreemodeHomeEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: FreemodeHomeEvent) {
        when (event) {
            is FreemodeHomeEvent.SelectDifficulty -> {
                _state.update { it.copy(selectedDifficulty = event.difficulty) }
            }
            is FreemodeHomeEvent.Play -> {
                viewModelScope.launch {
                    _effects.send(FreemodeHomeEffect.Play(state.value.selectedDifficulty))
                }
            }
        }
    }
}