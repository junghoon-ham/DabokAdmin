package com.hampson.dabokadmin.data.mapper

import com.hampson.dabokadmin.data.dto.MenuDto
import com.hampson.dabokadmin.domain.model.Menu

fun MenuDto.toMenu() = Menu(
    id = id ?: -1,
    name = name ?: "",
    type = type ?: -1,
    ingredient = ingredient ?: -1
)

fun List<MenuDto>.toMenus() = map { it.toMenu() }