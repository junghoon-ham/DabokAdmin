package com.hampson.dabokadmin.domain.use_case.validation

import com.hampson.dabokadmin.domain.model.Menu
import javax.inject.Inject

class ValidateMenus @Inject constructor() {

    fun execute(menus: List<Menu>): ValidationResult {
        if (menus.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "메뉴를 하나 이상 추가해 주세요."
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}