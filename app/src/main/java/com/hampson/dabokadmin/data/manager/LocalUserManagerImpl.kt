package com.hampson.dabokadmin.data.manager

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.hampson.dabokadmin.domain.manager.LocalUserManager
import com.hampson.dabokadmin.util.Constants
import com.hampson.dabokadmin.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserManagerImpl @Inject constructor(
    private val application: Application
): LocalUserManager {
    override suspend fun updateTheme() {
        val currentTheme = readTheme().first()

        application.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_THEME] = !currentTheme
        }
    }

    override fun readTheme(): Flow<Boolean> {
        return application.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_THEME] ?: false
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

private object PreferencesKeys {
    val APP_THEME = booleanPreferencesKey(name = Constants.APP_THEME)
}