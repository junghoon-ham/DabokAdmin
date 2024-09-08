package com.hampson.dabokadmin.presentation.meal_list

import com.hampson.dabokadmin.domain.model.Meal

sealed class MealFormEvent {
    data class OnDeleteMeal(val date: String) : MealFormEvent()
    data class OnUpdateMeal(val meal: Meal) : MealFormEvent()
    data class OnCopyMeal(val meal: Meal) : MealFormEvent()
}