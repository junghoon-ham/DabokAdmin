package com.hampson.dabokadmin.domain.repository

import com.hampson.dabokadmin.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveUserInfo(email: String)

    fun getUserInfo(): Flow<UserInfo>
}