package com.hampson.dabokadmin.data.dto

data class MealRegistrationRequest(
    val date: String,
    val menuIdList: List<Long>
)