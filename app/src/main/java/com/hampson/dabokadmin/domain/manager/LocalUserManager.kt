package com.hampson.dabokadmin.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun updateTheme()

    fun readTheme(): Flow<Boolean>
}