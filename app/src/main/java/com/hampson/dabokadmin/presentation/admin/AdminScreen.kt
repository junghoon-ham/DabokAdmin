package com.hampson.dabokadmin.presentation.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AdminScreen(navController: NavController) {

    val tabItems = listOf(
        TabItem(
            title = "카테고리 관리",
            unselectedIcon = Icons.Outlined.Category,
            selectedIcon = Icons.Filled.Category
        ),
        TabItem(
            title = "메뉴 관리",
            unselectedIcon = Icons.Outlined.FoodBank,
            selectedIcon = Icons.Filled.FoodBank
        ),
        TabItem(
            title = "원산지 관리",
            unselectedIcon = Icons.Outlined.CheckCircle,
            selectedIcon = Icons.Filled.CheckCircle
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                indicator = { tabPositions ->
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        width = 40.dp
                    )
                }
            ) {
                tabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(text = tabItem.title)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedTabIndex) {
                                    tabItem.selectedIcon
                                } else {
                                    tabItem.unselectedIcon
                                },
                                contentDescription = tabItem.title
                            )
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}