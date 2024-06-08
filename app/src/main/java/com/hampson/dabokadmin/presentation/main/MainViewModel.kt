package com.hampson.dabokadmin.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.domain.use_case.meal.MealUseCases
import com.hampson.dabokadmin.presentation.meal_list.MealState
import com.hampson.dabokadmin.util.Constants.SPLASH_HOLDING_TIME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.hampson.dabokadmin.util.Result

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mealsUseCase: MealUseCases
) : ViewModel() {

    var splashScreenDelay by mutableStateOf(true)

    private val _mealsState = MutableStateFlow(MealState())
    val mealsState = _mealsState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(SPLASH_HOLDING_TIME)
            splashScreenDelay = false
        }

        loadMealsResult()
    }

   fun loadMealsResult() {
       viewModelScope.launch {
           mealsUseCase.getMealsUseCase()
               .collect { result ->
                   when (result) {
                       is Result.Error -> Unit
                       is Result.Loading -> {
                           _mealsState.update {
                               it.copy(isLoading = result.isLoading)
                           }
                       }
                       is Result.Success -> {
                           delay(500)
                           result.data?.let { meals ->
                               _mealsState.value = _mealsState.value.copy(
                                   meals = meals
                               )
                           }
                       }
                   }
               }
       }
   }
}