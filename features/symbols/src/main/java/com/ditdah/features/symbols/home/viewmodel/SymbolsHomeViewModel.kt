package com.ditdah.features.symbols.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditdah.core.morse.domain.usecase.GetAlphabetUseCase
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SymbolsHomeViewModel @Inject constructor(
    private val settings: GetSettingsUseCase,
    private val alphabet: GetAlphabetUseCase
) : ViewModel() {
    val state =
        settings().map { SymbolsHomeState(alphabet = alphabet(), language = it.language) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SymbolsHomeState(
                alphabet = alphabet(),
                language = settings().value.language
            )
        )

}