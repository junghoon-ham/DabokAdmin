package com.hampson.dabokadmin.data.dto

import com.hampson.dabokadmin.domain.model.Menu

data class MealDto(
    val date: String? = null,
    val menuList: List<Menu>? = null
)