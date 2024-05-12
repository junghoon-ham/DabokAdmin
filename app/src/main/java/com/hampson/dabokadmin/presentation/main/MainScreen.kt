package com.hampson.dabokadmin.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hampson.dabokadmin.presentation.components.AppBarColor
import com.hampson.dabokadmin.presentation.navigation.Navigation
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme

@Composable
fun MainScreen(
    navController: NavController
) {
    DabokAdminTheme {
        AppBarColor()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Navigation(
                navController = navController
            )
        }
    }
}