package com.hampson.dabokadmin.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.presentation.admin.AdminScreen
import com.hampson.dabokadmin.presentation.home.HomeScreen
import com.hampson.dabokadmin.presentation.settings.SettingsScreen

@Composable
fun BottomNavigation(navController: NavController) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOfBottomNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            bottomNavController.navigate(navItem.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if (currentDestination?.route != BottomScreens.SettingsScreen.rout) {
                AddItemFAB(
                    onClick = {
                        navController.navigate(Route.REGISTER_SCREEN)
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomScreens.HomeScreen.rout,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = BottomScreens.HomeScreen.rout) {
                HomeScreen(navController = bottomNavController)
            }
            composable(route = BottomScreens.AdminScreen.rout) {
                AdminScreen(navController = bottomNavController)
            }
            composable(route = BottomScreens.SettingsScreen.rout) {
                SettingsScreen(navController = bottomNavController)
            }
        }
    }
}

@Composable
fun AddItemFAB(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        onClick = {
            onClick()
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "AddItemFAB"
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.register_menu)
            )
        }
    )
}