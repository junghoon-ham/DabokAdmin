package com.hampson.dabokadmin.domain.use_case.meal

import com.hampson.dabokadmin.domain.repository.MealRepository
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateMealUseCase @Inject constructor(
    private val repository: MealRepository
) {
    suspend operator fun invoke(
        date: String,
        menuIds: List<Long>
    ): Flow<Result<Unit>> {
        return repository.updateMeal(
            date = date,
            menuIds = menuIds
        ).flowOn(Dispatchers.Default)
    }
}