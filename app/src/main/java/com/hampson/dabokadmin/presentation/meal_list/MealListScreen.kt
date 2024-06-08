package com.hampson.dabokadmin.presentation.meal_list

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.hampson.dabokadmin.presentation.main.MainViewModel
import com.hampson.dabokadmin.ui.effect.MealShimmerComponent

@Composable
fun MealListScreen(navController: NavController) {

    val viewModel = hiltViewModel<MainViewModel>()
    val mealsState by viewModel.mealsState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current
    BackHandler(
        enabled = true
    ) {
        (context as Activity).finish()
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadMealsResult()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(mealsState.meals) { meal ->
                if (mealsState.isLoading) {
                    MealShimmerComponent()
                } else {
                    MealComponent(
                        meal = meal
                    )
                }
            }
        }
    }
}