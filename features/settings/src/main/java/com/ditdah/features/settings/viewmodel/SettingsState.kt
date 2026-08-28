package com.ditdah.features.settings.viewmodel

import com.ditdah.core.settings.domain.entity.Language

data class SettingsState(
    val isLanguageModalOpen: Boolean = false
)

sealed class SettingsEvent {
    data object LanguageModalChangeVisibility : SettingsEvent()
    data object ChangeTheme : SettingsEvent()
    data class ChangeCacheDuration(val days: Int) : SettingsEvent()
    data class ChangeLanguage(val language: Language) : SettingsEvent()
    data class ChangeWpm(val wpm: Int) : SettingsEvent()
}