package com.ditdah.core.settings.domain.repository

import com.ditdah.core.settings.domain.entity.Language
import com.ditdah.core.settings.domain.entity.Settings
import kotlinx.coroutines.flow.StateFlow

internal interface SettingsRepository {
    fun getSettings(): StateFlow<Settings>

    suspend fun change(isDark: Boolean? = null, wpm: Int? = null, language: Language? = null, daysToCache: Int? = null)
}