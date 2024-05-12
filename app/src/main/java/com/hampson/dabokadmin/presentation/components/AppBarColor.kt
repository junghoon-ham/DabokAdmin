package com.hampson.dabokadmin.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppBarColor() {
    val systemUiController = rememberSystemUiController()
    val color = MaterialTheme.colorScheme.primaryContainer
    LaunchedEffect(color) {
        systemUiController.setSystemBarsColor(color)
    }
}