package com.hampson.dabokadmin.data.dto

import com.google.gson.annotations.SerializedName

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
    val hasNext: Boolean? = false
)

data class Meta(
    @SerializedName("status")
    val status: Boolean? = null,
    @SerializedName("code")
    val code: Int? = null
)