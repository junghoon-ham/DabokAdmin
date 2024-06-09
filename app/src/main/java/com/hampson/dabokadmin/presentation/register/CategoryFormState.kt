package com.hampson.dabokadmin.presentation.register

import com.hampson.dabokadmin.domain.model.Category

data class CategoryFormState(
    val categories: List<Category> = emptyList()
)