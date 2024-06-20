package com.hampson.dabokadmin.presentation.meal_list

sealed class MealFormEvent {
    data class OnDeleteMeal(val date: String) : MealFormEvent()
    data class OnUpdateMeal(val date: String) : MealFormEvent()
}