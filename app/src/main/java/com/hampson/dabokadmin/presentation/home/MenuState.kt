package com.hampson.dabokadmin.presentation.home

import com.hampson.dabokadmin.domain.model.Meal

data class MealState(
    val isLoading: Boolean = false,
    val meals: List<Meal> = listOf()
)
