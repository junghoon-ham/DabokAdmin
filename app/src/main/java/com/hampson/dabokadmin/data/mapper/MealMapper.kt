package com.hampson.dabokadmin.data.mapper

import com.hampson.dabokadmin.data.dto.MealDto
import com.hampson.dabokadmin.domain.model.Meal

fun MealDto.toMeal() = Meal(
    date = date ?: "",
    menuList = menuList ?: listOf()
)

fun List<MealDto>.toMeals() = map { it.toMeal() }