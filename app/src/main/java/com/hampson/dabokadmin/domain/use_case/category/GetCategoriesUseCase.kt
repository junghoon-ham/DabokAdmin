package com.hampson.dabokadmin.domain.use_case.category

import com.hampson.dabokadmin.domain.model.Category
import com.hampson.dabokadmin.domain.repository.CategoryRepository
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Category>>> {
        return repository.getCategoriesResult().flowOn(Dispatchers.Default)
    }
}