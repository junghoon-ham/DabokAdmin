package com.hampson.dabokadmin.domain.use_case.manager

import com.hampson.dabokadmin.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke(): Flow<Boolean> {
        return localUserManager.readTheme()
    }
}