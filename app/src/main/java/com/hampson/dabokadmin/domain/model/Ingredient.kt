package com.hampson.dabokadmin.domain.model

// TODO: nullable 삭제
data class Ingredient(
    val id: Long? = null,
    val categoryId: Long? = null,
    val name: String? = null,

    var selected: Boolean = false
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf("$name")

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}