package com.hampson.dabokadmin.presentation.register

import com.hampson.dabokadmin.domain.model.Menu

data class MenuFormState(
    val isLoading: Boolean = false,
    val menus: List<Menu> = emptyList(),

    // paging
    val lastId: Long? = null,
    val hasNext: Boolean? = null,
    // search
    val words: String = ""
)