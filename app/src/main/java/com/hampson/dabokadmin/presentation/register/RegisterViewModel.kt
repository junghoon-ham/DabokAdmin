package com.hampson.dabokadmin.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.domain.model.Category
import com.hampson.dabokadmin.domain.model.Ingredient
import com.hampson.dabokadmin.domain.model.allCategories
import com.hampson.dabokadmin.domain.use_case.menu.MenuUseCases
import com.hampson.dabokadmin.domain.use_case.validation.ValidateDate
import com.hampson.dabokadmin.domain.use_case.validation.ValidateIngredients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
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

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _categories = MutableStateFlow(allCategories)
    val categories = searchText
        .combine(_categories) { text, categories ->
            if (text.isBlank()) {
                categories
            } else {
                categories.map { category ->
                    val filterIngredients = category.ingredients?.filter {
                        it.doesMatchSearchQuery(text)
                    }

                    category.copy(ingredients = filterIngredients)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _categories.value
        )

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

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}