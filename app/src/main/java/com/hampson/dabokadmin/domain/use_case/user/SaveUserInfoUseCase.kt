package com.hampson.dabokadmin.domain.use_case.user

import com.hampson.dabokadmin.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserInfoUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(email: String) {
        repository.saveUserInfo(email)
    }
}