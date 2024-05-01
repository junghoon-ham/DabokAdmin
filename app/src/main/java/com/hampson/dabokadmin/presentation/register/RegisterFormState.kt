package com.hampson.dabokadmin.presentation.register

import com.hampson.dabokadmin.domain.model.Menu

data class RegisterFormState(
    val date: String = "",
    val dateError: String? = null,
    val menus: List<Menu> = listOf(),
    val menusError: String? = null,
)