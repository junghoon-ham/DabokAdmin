package com.hampson.dabokadmin.domain.use_case.meal

data class MealUseCases (
    val registerMealUseCase: RegisterMealUseCase,
    val getMealUseCase: GetMealUseCase,
    val getMealsUseCase: GetMealsUseCase,
    val deleteMealUseCase: DeleteMealUseCase
)