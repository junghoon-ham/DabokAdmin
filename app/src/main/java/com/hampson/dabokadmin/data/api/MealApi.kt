package com.hampson.dabokadmin.data.api

import com.hampson.dabokadmin.data.dto.ApiResponse
import com.hampson.dabokadmin.data.dto.MealDto
import com.hampson.dabokadmin.data.dto.MealRegistrationRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApi {

    @POST("v1/meal")
    suspend fun registerMeal(
        @Body mealRequest: MealRegistrationRequest
    ): ApiResponse<Unit>

    @GET("v1/meal/list")
    suspend fun getMeals(
        @Query("date") date: String,
        @Query("size") size: Int
    ): ApiResponse<List<MealDto>>

    @DELETE("v1/meal/{date}")
    suspend fun deleteMeal(
        @Path("date") date: String
    ): ApiResponse<Unit>
}