package com.hampson.dabokadmin.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItems(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfBottomNavItems = listOf(
    BottomNavItems(
        label = "Home",
        icon = Icons.Default.Home,
        route = BottomScreens.HomeScreen.rout
    ),
    BottomNavItems(
        label = "Admin",
        icon = Icons.Default.Person,
        route = BottomScreens.AdminScreen.rout
    ),
    BottomNavItems(
        label = "Settings",
        icon = Icons.Default.Settings,
        route = BottomScreens.SettingsScreen.rout
    )
)