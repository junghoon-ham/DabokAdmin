package com.hampson.dabokadmin.domain.repository

import com.hampson.dabokadmin.domain.model.Meal
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    suspend fun registerMeal(
        date: String,
        menuIds: List<Long>
    ): Flow<Result<Unit>>

    suspend fun getMealsResult(): Flow<Result<List<Meal>>>
}