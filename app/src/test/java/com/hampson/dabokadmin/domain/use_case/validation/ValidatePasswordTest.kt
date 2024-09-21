package com.hampson.dabokadmin.domain.use_case.validation

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidatePasswordTest {

    private lateinit var validatePassword: ValidatePassword

    @Before
    fun setUp() {
        validatePassword = ValidatePassword()
    }

    @Test
    fun `Password must be at least 8 characters, return error`() {
        val result = validatePassword.execute("1234")

        assertEquals(result.successful, false)
    }
}