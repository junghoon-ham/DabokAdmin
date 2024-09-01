package com.hampson.dabokadmin.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hampson.dabokadmin.presentation.ManagerViewModel
import com.hampson.dabokadmin.presentation.navigation.Route
import com.hampson.dabokadmin.presentation.register.RegisterScreen
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val managerViewModel = hiltViewModel<ManagerViewModel>()

            val darkTheme by managerViewModel.darkTheme.collectAsState()

            DabokAdminTheme(darkTheme = darkTheme) {
                installSplashScreen().apply {
                    setKeepOnScreenCondition {
                        mainViewModel.splashScreenDelay
                    }
                }

                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.MAIN_SCREEN
    ) {
        composable(route = Route.MAIN_SCREEN) {
            MainScreen(
                navController = navController
            )
        }

        composable(route = "${Route.REGISTER_SCREEN}?date={date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            RegisterScreen(
                navController = navController,
                date = date
            )
        }
    }
}