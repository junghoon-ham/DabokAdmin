package com.hampson.dabokadmin.data.repository

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hampson.dabokadmin.domain.manager.LocalUserManager
import com.hampson.dabokadmin.domain.model.UserInfo
import com.hampson.dabokadmin.domain.repository.UserRepository
import com.hampson.dabokadmin.domain.use_case.user.UserInfoUseCases
import com.hampson.dabokadmin.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val application: Application
): UserRepository {

    companion object {
        val USER_EMAIL = stringPreferencesKey("user_mail")
    }

    override suspend fun saveUserInfo(email: String) {
        application.dataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
        }
    }

    override fun getUserInfo(): Flow<UserInfo> {
        return application.dataStore.data.map { preferences ->
            val email = preferences[USER_EMAIL] ?: ""
            UserInfo(email)
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.USER_INFO)