package com.ditdah.features.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditdah.core.settings.domain.usecase.ChangeSettingsUseCase
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettings: GetSettingsUseCase,
    private val change: ChangeSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()
    val settings = getSettings().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeTheme -> {
                viewModelScope.launch {
                    change(isDark = !(settings.value?.isDarkTheme ?: false))
                }
            }
            is SettingsEvent.ChangeWpm -> {
                viewModelScope.launch {
                    change(wpm = event.wpm)
                }
            }
            is SettingsEvent.ChangeLanguage -> {
                viewModelScope.launch {
                    change(language = event.language)
                }
                _state.update { it.copy(isLanguageModalOpen = false) }
            }
            is SettingsEvent.ChangeCacheDuration -> {
                viewModelScope.launch {
                    change(daysToCache = event.days)
                }
            }
            is SettingsEvent.LanguageModalChangeVisibility -> {
                _state.update { it.copy(isLanguageModalOpen = !state.value.isLanguageModalOpen) }
            }
        }
    }
}