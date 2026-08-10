package com.ditdah.core.settings.domain.repository

import com.ditdah.core.settings.domain.entity.Settings
import kotlinx.coroutines.flow.StateFlow

internal interface SettingsRepository {
    fun getSettings(): StateFlow<Settings>
}