package com.hampson.dabokadmin.presentation

import com.hampson.dabokadmin.domain.model.Menu

data class MenuState(
    val isLoading: Boolean = false,
    val menuId: Long = -1,
    val menu: Menu? = null
)
