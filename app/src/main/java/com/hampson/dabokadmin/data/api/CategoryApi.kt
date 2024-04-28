package com.hampson.dabokadmin.data.api

import com.hampson.dabokadmin.data.dto.ApiResponse
import com.hampson.dabokadmin.data.dto.CategoryDto
import retrofit2.http.GET

interface CategoryApi {

    @GET("v1/category")
    suspend fun getCategories(): ApiResponse<List<CategoryDto>>
}