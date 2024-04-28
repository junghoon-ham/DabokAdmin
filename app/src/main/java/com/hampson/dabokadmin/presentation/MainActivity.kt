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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hampson.dabokadmin.presentation.main.MainScreen
import com.hampson.dabokadmin.presentation.navigation.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val menuState by mainViewModel.menuState.collectAsState()
            val navController = rememberNavController()

            installSplashScreen().apply {
                setKeepOnScreenCondition {
                    mainViewModel.splashScreenDelay
                }
            }

            NavHost(
                navController = navController,
                startDestination = Route.MAIN_SCREEN
            ) {
                composable(route = Route.MAIN_SCREEN) {
                    MainScreen(
                        navController = navController,
                    )
                }
            }
        }
    }
}

@Composable
fun BarColor() {
    val systemUiController = rememberSystemUiController()
    val color = MaterialTheme.colorScheme.background
    LaunchedEffect(color) {
        systemUiController.setSystemBarsColor(color)
    }
}