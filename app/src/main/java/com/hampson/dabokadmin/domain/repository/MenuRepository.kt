package com.hampson.dabokadmin.domain.repository

import com.hampson.dabokadmin.domain.model.Menu
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    suspend fun getMenusResult(
        typeId: Int
    ): Flow<Result<List<Menu>>>
}