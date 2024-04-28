package com.hampson.dabokadmin.presentation.navigation

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.hampson.dabokadmin.presentation.register.RegisterActivity
import com.hampson.dabokadmin.presentation.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavController) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current

    var selectedScreen by remember { mutableStateOf(BottomNavItems.Home) }
    var isFABVisible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = currentDestination?.route) {
        isFABVisible = currentDestination?.route != BottomNavItems.Settings.route

        when (currentDestination?.route) {
            BottomNavItems.Home.route -> selectedScreen = BottomNavItems.Home
            BottomNavItems.Admin.route -> selectedScreen = BottomNavItems.Admin
            BottomNavItems.Settings.route -> selectedScreen = BottomNavItems.Settings
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = selectedScreen.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = selectedScreen.icon,
                            contentDescription = null
                        )
                    }
                },
                actions = {}
            )
        },
        bottomBar = {
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
            AnimatedVisibility(
                visible = isFABVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AddItemFAB(
                    onClick = {
                        context.startActivity(Intent(context, RegisterActivity::class.java))
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
private fun AddItemFAB(onClick: () -> Unit) {

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