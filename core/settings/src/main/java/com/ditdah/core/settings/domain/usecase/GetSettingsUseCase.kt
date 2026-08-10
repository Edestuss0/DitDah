package com.ditdah.core.settings.domain.usecase

import com.ditdah.core.settings.domain.entity.Settings
import com.ditdah.core.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSettingsUseCase @Inject internal constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): StateFlow<Settings> = repository.getSettings()
}