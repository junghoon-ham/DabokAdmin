package com.hampson.dabokadmin.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    Home(
        label = "Home",
        icon = Icons.Default.Home,
        route = BottomScreens.HomeScreen.rout
    ),
    Admin(
        label = "Admin",
        icon = Icons.Default.Person,
        route = BottomScreens.AdminScreen.rout
    ),
    Settings(
        label = "Settings",
        icon = Icons.Default.Settings,
        route = BottomScreens.SettingsScreen.rout
    )
}