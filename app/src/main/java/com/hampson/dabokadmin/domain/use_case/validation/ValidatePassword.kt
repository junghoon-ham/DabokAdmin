package com.hampson.dabokadmin.domain.use_case.validation

import android.util.Patterns
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "비밀번호를 8자 이상 입력해 주세요."
            )
        }

        //val containsLettersAndDigits = password.any { it.isDigit() }
        //        && password.any { it.isLetter() }
//
        //if (!containsLettersAndDigits) {
        //    return ValidationResult(
        //        successful = false,
        //        errorMessage = "1개 이상의 문자가 필요합니다."
        //    )
        //}

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}