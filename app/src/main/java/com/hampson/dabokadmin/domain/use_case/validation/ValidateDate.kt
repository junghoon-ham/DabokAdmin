package com.hampson.dabokadmin.domain.use_case.validation

class ValidateDate {

    fun execute(date: String): ValidationResult {
        if (date.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "날짜를 입력해 주세요."
            )
        }

        // TODO: 지난날짜, 이미 메뉴가 있는 날짜 유효성 검사 필요

        return ValidationResult(
            successful = true
        )
    }
}