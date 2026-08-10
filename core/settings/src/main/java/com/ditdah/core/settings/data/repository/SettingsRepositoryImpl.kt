package com.ditdah.core.settings.data.repository

import com.ditdah.core.di.ApplicationScope
import com.ditdah.core.settings.data.datastore.source.SettingsDataStoreSource
import com.ditdah.core.settings.domain.entity.Settings
import com.ditdah.core.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SettingsRepositoryImpl @Inject constructor(
    private val source: SettingsDataStoreSource,
    @ApplicationScope private val scope: CoroutineScope
) : SettingsRepository {

    private val settings: StateFlow<Settings> = source.getSettings().stateIn(
        scope = scope,
        initialValue = Settings(),
        started = SharingStarted.WhileSubscribed(5000)
    )

    override fun getSettings(): StateFlow<Settings> = settings
}