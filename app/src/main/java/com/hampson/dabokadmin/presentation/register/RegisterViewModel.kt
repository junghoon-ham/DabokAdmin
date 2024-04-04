package com.hampson.dabokadmin.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.domain.use_case.menu.MenuUseCases
import com.hampson.dabokadmin.domain.use_case.validation.ValidateDate
import com.hampson.dabokadmin.domain.use_case.validation.ValidateIngredients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val menuUseCases: MenuUseCases,
    private val validateDate: ValidateDate = ValidateDate(),
    private val validateIngredients: ValidateIngredients = ValidateIngredients()
): ViewModel() {

    var state by mutableStateOf(RegisterFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.DateChanged -> {
                state = state.copy(date = event.date)
            }
            is RegisterFormEvent.IngredientsChanged -> {
                state = state.copy(ingredients = event.ingredients)
            }
            is RegisterFormEvent.OriginsChanged -> {
                state = state.copy(origins = event.origins)
            }
            is RegisterFormEvent.MenuImagesChanged -> {
                state = state.copy(menuImages = event.menuImages)
            }
            is RegisterFormEvent.Submit -> {
                submitDate()
            }
        }
    }

    private fun submitDate() {
        val dateResult = validateDate.execute(state.date)
        val ingredientsResult = validateIngredients.execute(state.ingredients)
        val originsResult = state.origins
        val menuImagesResult = state.menuImages

        val hasError = listOf(
            dateResult,
            ingredientsResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                dateError = dateResult.errorMessage,
                ingredientsError = ingredientsResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}