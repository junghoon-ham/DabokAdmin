package com.hampson.dabokadmin.domain.model

// TODO: nullable 삭제
data class Category(
    val id: Long? = null,
    val name: String? = null,
    val ingredients: List<Ingredient>? = null
)

// TEST DATA
val allCategories = listOf(
    Category(
        name = "종류별",
        ingredients = listOf(
            Ingredient(name = "만두"),
            Ingredient(name = "소세지 야채볶음"),
            Ingredient(name = "시금치"),
            Ingredient(name = "콩나물 반찬"),
            Ingredient(name = "감자채볶음"),
            Ingredient(name = "계란말이"),
            Ingredient(name = "참치 무 조림"),
            Ingredient(name = "치킨너겟"),
            Ingredient(name = "장조림"),
            Ingredient(name = "산적꼬치"),
            Ingredient(name = "마늘쫑무침"),
            Ingredient(name = "고구마조림"),
        )
    ),
    Category(
        name = "상황별",
        ingredients = listOf(
            Ingredient(name = "골뱅이무침"),
            Ingredient(name = "두부김치"),
            Ingredient(name = "치킨"),
            Ingredient(name = "부추전"),
            Ingredient(name = "오징어볶음"),
            Ingredient(name = "수육"),
            Ingredient(name = "닭갈비"),
            Ingredient(name = "닭볶음탕"),
            Ingredient(name = "새우버터구이"),
            Ingredient(name = "주꾸미볶음"),
            Ingredient(name = "튀김"),
            Ingredient(name = "카나페"),
            Ingredient(name = "감자전"),
            Ingredient(name = "콘치즈"),
            Ingredient(name = "닭똥집볶음"),
        )
    ),
    Category(
        name = "재료별",
        ingredients = listOf(
            Ingredient(name = "소고기무국"),
            Ingredient(name = "소불고기"),
            Ingredient(name = "스테이크"),
            Ingredient(name = "육개장"),
            Ingredient(name = "LA갈비구이"),
            Ingredient(name = "나베"),
            Ingredient(name = "카레"),
            Ingredient(name = "갈비탕"),
            Ingredient(name = "떡갈비"),
            Ingredient(name = "파스타"),
            Ingredient(name = "비빔국수"),
            Ingredient(name = "스파게티"),
            Ingredient(name = "전병"),
            Ingredient(name = "짬뽕"),
            Ingredient(name = "냉면"),
            Ingredient(name = "소고기무국"),
            Ingredient(name = "소불고기"),
            Ingredient(name = "스테이크"),
            Ingredient(name = "육개장"),
            Ingredient(name = "LA갈비구이"),
            Ingredient(name = "나베"),
            Ingredient(name = "카레"),
            Ingredient(name = "갈비탕"),
            Ingredient(name = "떡갈비"),
            Ingredient(name = "파스타"),
            Ingredient(name = "비빔국수"),
            Ingredient(name = "스파게티"),
            Ingredient(name = "전병"),
            Ingredient(name = "짬뽕"),
            Ingredient(name = "냉면"),
        )
    )
)