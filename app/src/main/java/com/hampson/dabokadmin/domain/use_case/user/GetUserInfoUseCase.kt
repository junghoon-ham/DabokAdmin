package com.hampson.dabokadmin.domain.use_case.user

import com.hampson.dabokadmin.data.dto.Payload
import com.hampson.dabokadmin.domain.model.Menu
import com.hampson.dabokadmin.domain.model.UserInfo
import com.hampson.dabokadmin.domain.repository.MenuRepository
import com.hampson.dabokadmin.domain.repository.UserRepository
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Flow<UserInfo> {
        return repository.getUserInfo().flowOn(Dispatchers.Default)
    }
}