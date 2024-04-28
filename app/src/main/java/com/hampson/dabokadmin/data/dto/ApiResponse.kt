package com.hampson.dabokadmin.data.dto

data class ApiResponse<T>(
    val payload: Payload<T>,
    val meta: Meta
)

data class Payload<T>(
    val data: T
)

data class Meta(
    val status: Boolean? = null,
    val code: Int? = null
)