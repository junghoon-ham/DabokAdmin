package com.hampson.dabokadmin.domain.use_case.validation

import com.hampson.dabokadmin.domain.model.Menu
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidateMenusTest {

    private lateinit var validateMenus: ValidateMenus

    @Before
    fun setUp() {
        validateMenus = ValidateMenus()
    }

    @Test
    fun `menu list is empty, returns error`() {
        val menus = listOf(
            Menu(id = 0, name = "김치", type = 0, ingredient = 0),
            Menu(id = 1, name = "밥", type = 0, ingredient = 0),
            Menu(id = 2, name = "국", type = 0, ingredient = 0)
        )

        val result = validateMenus.execute(menus)

        assertTrue(result.successful)
        assertNull(result.errorMessage)
    }
}