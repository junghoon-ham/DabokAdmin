package com.hampson.dabokadmin.domain.model

data class Menu(
    val id: Long,
    val name: String,
    val type: Long,
    val ingredient: Int
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(name)

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

data class MenusResponse(
    val menus: List<Menu>,
    val hasNext: Boolean
)