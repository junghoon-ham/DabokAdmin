package com.hampson.dabokadmin.domain.use_case.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)