package com.hampson.dabokadmin.presentation.meal_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.domain.model.Meal
import com.hampson.dabokadmin.domain.use_case.meal.MealUseCases
import com.hampson.dabokadmin.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealsUseCase: MealUseCases
) : ViewModel() {

    private val _mealsState = MutableStateFlow(MealState())
    val mealsState = _mealsState.asStateFlow()

    private val _deleteSuccess = MutableStateFlow(false)
    val deleteSuccess = _deleteSuccess.asSharedFlow()

    private val _navigateToRegisterScreen = MutableSharedFlow<Meal>()
    val navigateToRegisterScreen: SharedFlow<Meal> get() = _navigateToRegisterScreen

    private val _navigateToUpdateScreen = MutableSharedFlow<Meal>()
    val navigateToUpdateScreen: SharedFlow<Meal> get() = _navigateToUpdateScreen

    private val _successEvent = MutableSharedFlow<String>()
    val successEvent = _successEvent.asSharedFlow()

    init {
        loadMealsResult()
    }

    fun onEvent(event: MealFormEvent) {
        when (event) {
            is MealFormEvent.OnDeleteMeal -> {
                deleteMeal(event.date)
            }
            is MealFormEvent.OnUpdateMeal -> {
                viewModelScope.launch {
                    _navigateToUpdateScreen.emit(event.meal)
                }
            }
            is MealFormEvent.OnCopyMeal -> {
                viewModelScope.launch {
                    _navigateToRegisterScreen.emit(event.meal)
                }
            }
        }
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

    private fun deleteMeal(date: String) {
        viewModelScope.launch {
            mealsUseCase.deleteMealUseCase(
                date = date
            ).collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        _deleteSuccess.value = true
                        _successEvent.emit("삭제가 완료되었습니다.")

                        _mealsState.update { currentState ->
                            currentState.copy(
                                meals = currentState.meals.filterNot { it.date == date }
                            )
                        }
                    }
                }
            }
        }
    }
}