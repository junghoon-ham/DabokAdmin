package com.hampson.dabokadmin.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.presentation.admin.AdminScreen
import com.hampson.dabokadmin.presentation.meal_list.MealListScreen
import com.hampson.dabokadmin.presentation.navigation.BottomNavItems
import com.hampson.dabokadmin.presentation.navigation.BottomScreens
import com.hampson.dabokadmin.presentation.navigation.Route
import com.hampson.dabokadmin.presentation.register.UserActionType
import com.hampson.dabokadmin.presentation.settings.SettingsScreen

@Composable
fun MainScreen(
    navController: NavController
) {

    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var selectedScreen by remember { mutableStateOf(BottomNavItems.MealList) }
    var isFABVisible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = currentDestination?.route) {
        isFABVisible = currentDestination?.route == BottomNavItems.MealList.route

        when (currentDestination?.route) {
            BottomNavItems.MealList.route -> selectedScreen = BottomNavItems.MealList
            BottomNavItems.Admin.route -> selectedScreen = BottomNavItems.Admin
            BottomNavItems.Settings.route -> selectedScreen = BottomNavItems.Settings
        }
    }

    Scaffold(
        content = { paddingValues ->
            BottomNavigationScreens(
                selectedScreen = selectedScreen,
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                navController = navController,
                bottomNavController = bottomNavController
            )
        },
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = bottomNavController,
                selectedScreen = selectedScreen
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isFABVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AddItemFAB(
                    onClick = {
                        navController.navigate(route = "${Route.REGISTER_SCREEN}/${UserActionType.REGISTER}")
                    }
                )
            }
        }
    )
}

@Composable
private fun AddItemFAB(onClick: () -> Unit) {

    ExtendedFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.medium,
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
                text = stringResource(id = R.string.register_meal_action)
            )
        }
    )
}

@Composable
fun BottomNavigationScreens(
    selectedScreen: BottomNavItems,
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomNavController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = bottomNavController,
        startDestination = BottomScreens.MealListScreen.rout,
    ) {
        composable(route = BottomScreens.MealListScreen.rout) {
            MealListScreen(navController = navController)
        }
        composable(route = BottomScreens.AdminScreen.rout) {
            AdminScreen(navController = bottomNavController)
        }
        composable(route = BottomScreens.SettingsScreen.rout) {
            SettingsScreen(navController = bottomNavController)
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
    selectedScreen: BottomNavItems,
) {
    NavigationBar {
        BottomNavItems.values().forEach { navItem ->
            NavigationBarItem(
                selected = selectedScreen == navItem,
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
                        imageVector = if (selectedScreen == navItem) {
                            navItem.selectedIcon
                        } else {
                            navItem.unselectedIcon
                        },
                        contentDescription = null,
                    )
                },
                label = {
                    Text(text = navItem.label)
                }
            )
        }
    }
}