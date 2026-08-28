package com.ditdah.core.settings.data.datastore.entities

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal val WPM = intPreferencesKey("wpm")
internal val LANGUAGE = stringPreferencesKey("language")
internal val IS_DARK = booleanPreferencesKey("is_dark")
internal val DAYS_TO_CACHE = intPreferencesKey("days_to_cache")
