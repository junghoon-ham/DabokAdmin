package com.hampson.dabokadmin.presentation.register

import com.hampson.dabokadmin.domain.model.Ingredient
import com.hampson.dabokadmin.domain.model.Origin

sealed class RegisterFormEvent {
    data class DateChanged(val date: String) : RegisterFormEvent()
    data class IngredientsChanged(val ingredients: List<Ingredient>) : RegisterFormEvent()
    data class OriginsChanged(val origins: List<Origin>) : RegisterFormEvent()
    data class MenuImagesChanged(val menuImages: List<String>) : RegisterFormEvent()

    object Submit : RegisterFormEvent()
}