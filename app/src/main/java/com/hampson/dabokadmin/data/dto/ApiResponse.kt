package com.hampson.dabokadmin.data.dto

import com.google.gson.annotations.SerializedName
import com.hampson.dabokadmin.domain.model.Menu

data class ApiResponse<T>(
    @SerializedName("payload")
    val payload: Payload<T?>,
    @SerializedName("meta")
    val meta: Meta
)

data class Payload<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("hasNext")
    val hasNext: Boolean? = false,

    @SerializedName("date")
    val date: String? = null,
    @SerializedName("menuList")
    val menuList: List<Menu>? = null
)

data class Meta(
    @SerializedName("status")
    val status: Boolean? = null,
    @SerializedName("code")
    val code: Int? = null
)