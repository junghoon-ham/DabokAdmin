package com.hampson.dabokadmin.presentation.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hampson.dabokadmin.presentation.BarColor
import com.hampson.dabokadmin.presentation.navigation.BottomNavigation
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme

@Composable
fun MainScreen(
    navController: NavController
) {
    DabokAdminTheme {
        BarColor()

        BottomNavigation(
            navController = navController
        )
    }
}