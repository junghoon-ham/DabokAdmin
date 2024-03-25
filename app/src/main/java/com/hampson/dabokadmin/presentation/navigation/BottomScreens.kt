package com.hampson.dabokadmin.presentation.navigation

sealed class BottomScreens(val rout: String) {
    object HomeScreen: BottomScreens("home_screen")
    object AdminScreen: BottomScreens("admin_screen")
    object SettingsScreen: BottomScreens("settings_screen")
}