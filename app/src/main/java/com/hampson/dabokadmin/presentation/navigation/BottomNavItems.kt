package com.hampson.dabokadmin.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    MealList(
        label = "식단 리스트",
        selectedIcon = Icons.Filled.Checklist,
        unselectedIcon = Icons.Outlined.Checklist,
        route = BottomScreens.MealListScreen.rout
    ),
    Admin(
        label = "관리자",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = BottomScreens.AdminScreen.rout
    ),
    Settings(
        label = "설정",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        route = BottomScreens.SettingsScreen.rout
    )
}