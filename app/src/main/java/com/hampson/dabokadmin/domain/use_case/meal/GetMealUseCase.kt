package com.hampson.dabokadmin.domain.use_case.meal

import com.hampson.dabokadmin.domain.model.Meal
import com.hampson.dabokadmin.domain.repository.MealRepository
import com.hampson.dabokadmin.util.DateUtil
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMealUseCase @Inject constructor(
    private val repository: MealRepository
) {
    suspend operator fun invoke(
        date: String
    ): Flow<Result<Meal>> {
        return repository.getMealResult(date).flowOn(Dispatchers.Default)
    }
}