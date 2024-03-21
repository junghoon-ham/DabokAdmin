package com.hampson.dabokadmin.data.api

import com.hampson.dabokadmin.data.dto.MenuDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MenuApi {

    @GET("{menuId}")
    suspend fun getMenuId(
        @Path("menuId") menuId: Long
    ): MenuDto
}