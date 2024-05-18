package com.hampson.dabokadmin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hampson.dabokadmin.presentation.main.MainScreen
import com.hampson.dabokadmin.presentation.navigation.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel = hiltViewModel<MainViewModel>()
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
                        navController = navController
                    )
                }
            }
        }
    }
}