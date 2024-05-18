package com.hampson.dabokadmin.presentation.navigation

sealed class BottomScreens(val rout: String) {
    object MealListScreen: BottomScreens("meal_list_screen")
    object AdminScreen: BottomScreens("admin_screen")
    object SettingsScreen: BottomScreens("settings_screen")
}