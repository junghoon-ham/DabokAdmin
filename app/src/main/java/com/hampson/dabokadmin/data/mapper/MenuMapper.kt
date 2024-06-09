package com.hampson.dabokadmin.data.mapper

import com.hampson.dabokadmin.data.dto.MenuDto
import com.hampson.dabokadmin.data.dto.Payload
import com.hampson.dabokadmin.domain.model.Menu
import com.hampson.dabokadmin.domain.model.MenusResponse

fun MenuDto.toMenu() = Menu(
    id = id ?: -1,
    name = name ?: "",
    type = type ?: -1,
    ingredient = ingredient ?: -1
)

fun List<MenuDto>.toMenus() = map { it.toMenu() }

fun Payload<List<MenuDto>?>.toPayload(): Payload<List<Menu>> = Payload(
    data = this.data?.toMenus(),
    hasNext = this.hasNext ?: false
)