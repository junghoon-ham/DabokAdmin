package com.hampson.dabokadmin.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItems(
        label = "Home",
        icon = Icons.Default.Home,
        route = MainScreens.HomeScreen.name
    ),
    NavItems(
        label = "Admin",
        icon = Icons.Default.Person,
        route = MainScreens.AdminScreen.name
    ),
    NavItems(
        label = "Settings",
        icon = Icons.Default.Settings,
        route = MainScreens.SettingsScreen.name
    )
)