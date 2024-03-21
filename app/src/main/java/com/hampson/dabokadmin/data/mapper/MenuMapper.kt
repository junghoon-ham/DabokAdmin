package com.hampson.dabokadmin.data.mapper

import com.hampson.dabokadmin.data.dto.MenuDto
import com.hampson.dabokadmin.domain.model.Menu

fun MenuDto.toMenu() = Menu(
    id = id ?: -1
)













