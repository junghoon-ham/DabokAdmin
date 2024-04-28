package com.hampson.dabokadmin.presentation.register

import com.hampson.dabokadmin.domain.model.Menu

data class MenuFormState(
    val menus: List<Menu> = listOf()
)