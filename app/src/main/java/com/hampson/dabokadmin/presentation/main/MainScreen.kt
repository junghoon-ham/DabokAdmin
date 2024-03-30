package com.hampson.dabokadmin.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hampson.dabokadmin.presentation.BarColor
import com.hampson.dabokadmin.presentation.navigation.Navigation
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme

@Composable
fun MainScreen(
    navController: NavController
) {
    DabokAdminTheme {
        BarColor()

        Navigation(
            navController = navController
        )
    }
}