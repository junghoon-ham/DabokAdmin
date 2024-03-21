package com.hampson.dabokadmin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hampson.dabokadmin.presentation.navigation.AppNavigation
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DabokAdminTheme {
                BarColor()

                val mainViewModel = hiltViewModel<MainViewModel>()
                val menuState by mainViewModel.menuState.collectAsState()

                installSplashScreen().apply {
                    setKeepOnScreenCondition {
                        mainViewModel.splashScreenDelay
                    }
                }

                AppNavigation()
            }
        }
    }

    @Composable
    private fun BarColor() {
        val systemUiController = rememberSystemUiController()
        val color = MaterialTheme.colorScheme.background
        LaunchedEffect(color) {
            systemUiController.setSystemBarsColor(color)
        }
    }
}