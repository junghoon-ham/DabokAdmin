package com.hampson.dabokadmin.domain.use_case.validation

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidateEmailTest {

    private lateinit var validateEmail: ValidateEmail

    @Before
    fun setUp() {
        validateEmail = ValidateEmail()
    }

    @Test
    fun `email is empty, returns error`() {
        val email = ""

        val result = validateEmail.execute(email)

        assertFalse(result.successful)
        assertEquals("이메일을 입력해 주세요.", result.errorMessage)
    }

    @Test // 이미 이메일 정규식 사용중이어서 필요하지 않다.
    fun `email format is incorrect, returns error`() {
        val email = "admin-com"

        val result = validateEmail.execute(email)

        assertFalse(result.successful)
        assertEquals("이메일 형식에 맞지 않습니다.", result.errorMessage)
    }
}