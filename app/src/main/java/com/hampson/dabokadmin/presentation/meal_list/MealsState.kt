package com.hampson.dabokadmin.presentation.meal_list

import com.hampson.dabokadmin.domain.model.Meal

data class MealState(
    val isLoading: Boolean = false,
    val meals: List<Meal> = listOf()
)
