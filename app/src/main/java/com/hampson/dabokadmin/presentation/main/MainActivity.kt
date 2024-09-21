package com.hampson.dabokadmin.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.hampson.dabokadmin.presentation.register.UserActionType
import com.hampson.dabokadmin.presentation.sign_in.SignInScreen
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
            val hasUserInfo by mainViewModel.hasUserInfo.collectAsState()

            DabokAdminTheme(darkTheme = darkTheme) {
                installSplashScreen().apply {
                    setKeepOnScreenCondition {
                        mainViewModel.splashScreenDelay
                    }
                }

                hasUserInfo?.let {
                    Navigation(startDestination = if (hasUserInfo == true) {
                        Route.MAIN_SCREEN
                    } else {
                        Route.SIGN_IN
                    })
                }
            }
        }
    }
}

@Composable
fun Navigation(startDestination: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Route.SIGN_IN) {
            SignInScreen(
                navController = navController
            )
        }

        composable(route = Route.MAIN_SCREEN) {
            MainScreen(
                navController = navController
            )
        }

        composable(route = "${Route.REGISTER_SCREEN}/{userActionType}?date={date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")

            val userActionTypeStr = backStackEntry.arguments?.getString("userActionType")
            val userActionType = userActionTypeStr?.let { UserActionType.valueOf(it) }

            RegisterScreen(
                navController = navController,
                date = date,
                userActionType = userActionType ?: UserActionType.REGISTER
            )
        }
    }
}