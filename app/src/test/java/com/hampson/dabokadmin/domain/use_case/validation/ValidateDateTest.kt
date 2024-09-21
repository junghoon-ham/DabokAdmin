package com.hampson.dabokadmin.domain.use_case.validation

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidateDateTest {

    private lateinit var validateDate: ValidateDate

    @Before
    fun setUp() {
        validateDate = ValidateDate()
    }

    @Test
    fun `date is empty, returns error`() {
        val date = "2020-05-20"

        val result = validateDate.execute(date)

        assertEquals(result.successful, true)
    }
}