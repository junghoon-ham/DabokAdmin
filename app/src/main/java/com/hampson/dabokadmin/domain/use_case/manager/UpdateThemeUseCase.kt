package com.hampson.dabokadmin.domain.use_case.manager

import com.hampson.dabokadmin.domain.manager.LocalUserManager
import javax.inject.Inject

class UpdateThemeUseCase @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke() {
        localUserManager.updateTheme()
    }
}