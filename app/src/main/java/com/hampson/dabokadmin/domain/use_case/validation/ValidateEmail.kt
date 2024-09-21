package com.hampson.dabokadmin.domain.use_case.validation

import android.util.Patterns
import javax.inject.Inject

class ValidateEmail @Inject constructor() {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "이메일을 입력해 주세요."
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "이메일 형식에 맞지 않습니다."
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}