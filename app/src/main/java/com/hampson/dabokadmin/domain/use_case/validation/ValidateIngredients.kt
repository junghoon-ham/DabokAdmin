package com.hampson.dabokadmin.domain.use_case.validation

import com.hampson.dabokadmin.domain.model.Ingredient

class ValidateIngredients {

    fun execute(ingredient: List<Ingredient>): ValidationResult {
        if (ingredient.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "재료를 하나 이상 추가해 주세요."
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}