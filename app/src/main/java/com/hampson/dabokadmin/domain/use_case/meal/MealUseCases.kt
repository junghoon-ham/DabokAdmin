package com.hampson.dabokadmin.domain.use_case.meal

data class MealUseCases (
    val registerMealUseCase: RegisterMealUseCase,
    val getMealsUseCase: GetMealsUseCase,
    val deleteMealUseCase: DeleteMealUseCase
)