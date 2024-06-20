package com.hampson.dabokadmin.domain.use_case.meal

import com.hampson.dabokadmin.domain.model.Meal
import com.hampson.dabokadmin.domain.repository.MealRepository
import com.hampson.dabokadmin.util.Constants.PAGE_SIZE
import com.hampson.dabokadmin.util.DateUtil
import com.hampson.dabokadmin.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMealsUseCase @Inject constructor(
    private val repository: MealRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Meal>>> {
        val date = DateUtil.getTomorrowDate()
        val size = PAGE_SIZE
        return repository.getMealsResult(date, size).flowOn(Dispatchers.Default)
    }
}