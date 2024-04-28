package com.hampson.dabokadmin.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.domain.model.Category
import com.hampson.dabokadmin.domain.model.Menu
import com.hampson.dabokadmin.domain.use_case.category.CategoryUseCases
import com.hampson.dabokadmin.domain.use_case.menu.MenuUseCases
import com.hampson.dabokadmin.domain.use_case.validation.ValidateDate
import com.hampson.dabokadmin.domain.use_case.validation.ValidateIngredients
import com.hampson.dabokadmin.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCases,
    private val menuUseCases: MenuUseCases,
    private val validateDate: ValidateDate = ValidateDate(),
    private val validateIngredients: ValidateIngredients = ValidateIngredients()
): ViewModel() {

    var registerState by mutableStateOf(RegisterFormState())

    var selectedCategory by mutableStateOf(Category())

    private val _categoriesState = MutableStateFlow(CategoryFormState())
    val categoriesState = _categoriesState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _menusState = MutableStateFlow(MenuFormState())
    val menusState = searchText
        .debounce(300L)
        .onEach { _isSearching.update { true } }
        .combine(_menusState) { text, menus ->
            if (text.isBlank()) {
                menus
            } else {
                MenuFormState(menus = menus.menus.filter {
                    it.doesMatchSearchQuery(text)
                })
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _menusState.value
        )
    var selectedMenus = mutableStateListOf<Menu>()
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        loadCategoriesResult()
    }

    private fun loadCategoriesResult() {
        viewModelScope.launch {
            categoryUseCase.getCategoriesUseCase().collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        result.data?.let { categories ->
                            _categoriesState.value = _categoriesState.value.copy(
                                categories = categories
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadMenusResult(typeId: Int) {
        viewModelScope.launch {
            menuUseCases.getMenusUseCase(typeId).collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        result.data?.let { menus ->
                            _menusState.value = _menusState.value.copy(
                                menus = menus
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.DateChanged -> {
                registerState = registerState.copy(date = event.date)
            }
            is RegisterFormEvent.IngredientsChanged -> {
                registerState = registerState.copy(ingredients = event.ingredients)
            }
            is RegisterFormEvent.OriginsChanged -> {
                registerState = registerState.copy(origins = event.origins)
            }
            is RegisterFormEvent.MenuImagesChanged -> {
                registerState = registerState.copy(menuImages = event.menuImages)
            }
            is RegisterFormEvent.Submit -> {
                submitDate()
            }
        }
    }

    private fun submitDate() {
        val dateResult = validateDate.execute(registerState.date)
        val ingredientsResult = validateIngredients.execute(registerState.ingredients)
        val originsResult = registerState.origins
        val menuImagesResult = registerState.menuImages

        val hasError = listOf(
            dateResult,
            ingredientsResult
        ).any { !it.successful }

        if (hasError) {
            registerState = registerState.copy(
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

    fun onSelectedCategory(category: Category) {
        selectedCategory = category
        loadMenusResult(category.id.toInt())
    }

    fun onSelectedMenu(menu: Menu) {
        if (selectedMenus.contains(menu)) {
            selectedMenus.remove(menu)
        } else {
            selectedMenus.add(menu)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}