package com.ditdah.core.settings.data.datastore.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ditdah.core.settings.data.datastore.entities.LANGUAGE
import com.ditdah.core.settings.data.datastore.entities.WPM
import com.ditdah.core.settings.domain.entity.Language
import com.ditdah.core.settings.domain.entity.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_datastore")

internal class SettingsDataStoreSource @Inject constructor(
    @ApplicationContext context: Context
) {
    val dataStore = context.applicationContext.dataStore

    fun getSettings(): Flow<Settings> {
        return dataStore.data.map {
            Settings(
                wpm = it[WPM] ?: 10,
                language = Language.valueOf((it[LANGUAGE] ?: "EN").uppercase())
            )
        }
    }
}