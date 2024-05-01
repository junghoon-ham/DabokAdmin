package com.hampson.dabokadmin.presentation.register

import com.hampson.dabokadmin.domain.model.Menu

sealed class RegisterFormEvent {
    data class DateChanged(val date: String) : RegisterFormEvent()
    data class MenuChanged(val menu: Menu) : RegisterFormEvent()

    object Submit : RegisterFormEvent()
}