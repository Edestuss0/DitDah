package com.ditdah.core.settings.domain.usecase

import com.ditdah.core.settings.domain.entity.Language
import com.ditdah.core.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class ChangeSettingsUseCase @Inject internal constructor(
    private val repository: SettingsRepository
){
    suspend operator fun invoke(isDark: Boolean? = null, wpm: Int? = null, language: Language? = null, daysToCache: Int? = null) = repository.change(isDark, wpm, language, daysToCache)
}