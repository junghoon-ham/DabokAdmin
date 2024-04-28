package com.hampson.dabokadmin.data.api

import com.hampson.dabokadmin.data.dto.ApiResponse
import com.hampson.dabokadmin.data.dto.MenuDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MenuApi {

    @GET("v1/menu")
    suspend fun getMenus(
        @Query("typeId") typeId: Int
    ): ApiResponse<List<MenuDto>>
}