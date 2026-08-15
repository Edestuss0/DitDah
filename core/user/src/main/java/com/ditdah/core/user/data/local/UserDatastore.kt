package com.ditdah.core.user.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val IS_AUTHORIZED = booleanPreferencesKey("is_authorized")


internal class UserDatastore @Inject constructor(
 @ApplicationContext private val context: Context
) {
 private val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = "user_data")
 private val datastore = context.datastore

 fun get(): Flow<Boolean> = datastore.data.map { it[IS_AUTHORIZED] ?: false }

 suspend fun authorize() = datastore.edit { it[IS_AUTHORIZED] = true }
 suspend fun unauthorize() = datastore.edit { it[IS_AUTHORIZED] = false }
}