package com.hampson.dabokadmin.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    MealList(
        label = "식단 리스트",
        icon = Icons.Default.Checklist,
        route = BottomScreens.MealListScreen.rout
    ),
    Admin(
        label = "관리자",
        icon = Icons.Default.Person,
        route = BottomScreens.AdminScreen.rout
    ),
    Settings(
        label = "설정",
        icon = Icons.Default.Settings,
        route = BottomScreens.SettingsScreen.rout
    )
}