package com.ditdah.features.symbols.home.viewmodel

import androidx.lifecycle.ViewModel
import com.ditdah.core.morse.domain.usecase.GetAlphabetUseCase
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SymbolsHomeViewModel @Inject constructor(
    private val settings: GetSettingsUseCase,
    private val alphabet: GetAlphabetUseCase
) : ViewModel() {
    private val lang = settings().value.language
    private val _state = MutableStateFlow(SymbolsHomeState(alphabet = alphabet(), language = lang))
    val state = _state.asStateFlow()
}