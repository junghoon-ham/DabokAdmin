package com.hampson.dabokadmin.data.api

import com.hampson.dabokadmin.data.dto.ApiResponse
import com.hampson.dabokadmin.data.dto.MealRegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface MealApi {

    @POST("v1/meal")
    suspend fun registerMeal(
        @Body mealRequest: MealRegistrationRequest
    ): ApiResponse<Unit>
}