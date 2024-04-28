package com.hampson.dabokadmin.domain.repository

import com.hampson.dabokadmin.domain.model.Category
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategoriesResult(): Flow<Result<List<Category>>>
}