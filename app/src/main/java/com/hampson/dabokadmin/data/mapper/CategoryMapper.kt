package com.hampson.dabokadmin.data.mapper

import com.hampson.dabokadmin.data.dto.CategoryDto
import com.hampson.dabokadmin.domain.model.Category

fun CategoryDto.toCategory() = Category(
    id = id ?: -1,
    label = label ?: "",
    group = group ?: ""
)

fun List<CategoryDto>.toCategories() = map { it.toCategory() }