package com.ditdah.core.settings.data.datastore.source

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ditdah.core.settings.data.datastore.entities.DAYS_TO_CACHE
import com.ditdah.core.settings.data.datastore.entities.IS_DARK
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
    @ApplicationContext private val context: Context
) {
    val dataStore = context.applicationContext.dataStore

    fun getSettings(): Flow<Settings> {
        return dataStore.data.map {

            val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            val isSystemDark = currentNightMode == Configuration.UI_MODE_NIGHT_YES

            Settings(
                wpm = it[WPM] ?: 10,
                language = Language.valueOf((it[LANGUAGE] ?: "EN").uppercase()),
                isDarkTheme = it[IS_DARK] ?: isSystemDark,
                daysToCache = it[DAYS_TO_CACHE] ?: 3
            )
        }
    }

    suspend fun change(isDark: Boolean? = null, wpm: Int? = null, language: Language? = null, daysToCache: Int? = null) {
        dataStore.edit { preferences ->
            isDark?.let {
                preferences[IS_DARK] = isDark
            }
            wpm?.let {
                preferences[WPM] = wpm
            }
            language?.let {
                preferences[LANGUAGE] = language.name.uppercase()
            }
            daysToCache?.let {
                preferences[DAYS_TO_CACHE] = daysToCache
            }
        }
    }
}