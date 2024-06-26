package com.hampson.dabokadmin.data.api

import com.hampson.dabokadmin.data.dto.ApiResponse
import com.hampson.dabokadmin.data.dto.MenuDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MenuApi {

    @GET("v1/menu")
    suspend fun getMenus(
        @Query("typeId") typeId: Long,
        @Query("lastId") lastId: Long?,
        @Query("words") words: String?
    ): ApiResponse<List<MenuDto>>
}