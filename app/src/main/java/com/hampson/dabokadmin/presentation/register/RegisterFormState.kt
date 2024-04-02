package com.hampson.dabokadmin.presentation.register

import com.hampson.dabokadmin.domain.model.Ingredient
import com.hampson.dabokadmin.domain.model.Origin

data class RegisterFormState(
    val date: String = "",
    val dateError: String? = null,
    val ingredients: List<Ingredient> = listOf(),
    val ingredientsError: String? = null,
    val origins: List<Origin>? = null,
    val menuImages: List<String>? = null
)