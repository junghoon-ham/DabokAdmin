package com.hampson.dabokadmin.presentation.meal_list

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    val viewModel = hiltViewModel<MealViewModel>()
    val mealsState by viewModel.mealsState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current as? Activity

    val success by viewModel.deleteSuccess.collectAsState(false)

    BackHandler(enabled = true) {
        context?.finish()
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadMealsResult()
            }
        })
    }

    LaunchedEffect(success) {
        viewModel.successEvent.collect {  message ->
            navController.navigateUp()

            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if (mealsState.isLoading) {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(10) {
                    MealShimmerComponent()
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mealsState.meals, key = { it.date }) { meal ->
                    var isVisible by remember { mutableStateOf(true) }

                    AnimatedVisibility(
                        visible = isVisible,
                        exit = fadeOut()
                    ) {
                        MealComponent(
                            meal = meal,
                            onDelete = {
                                isVisible = false
                                viewModel.onEvent(MealFormEvent.OnDeleteMeal(meal.date))
                            },
                            onUpdate = { viewModel.onEvent(MealFormEvent.OnUpdateMeal(meal.date)) },
                        )
                    }
                }
            }
        }
    }
}