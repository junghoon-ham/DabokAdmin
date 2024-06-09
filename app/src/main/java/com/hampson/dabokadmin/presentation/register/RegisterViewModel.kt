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
import com.hampson.dabokadmin.domain.use_case.meal.MealUseCases
import com.hampson.dabokadmin.domain.use_case.menu.MenuUseCases
import com.hampson.dabokadmin.domain.use_case.manager.ManagerUseCases
import com.hampson.dabokadmin.domain.use_case.validation.ValidateDate
import com.hampson.dabokadmin.domain.use_case.validation.ValidateMenus
import com.hampson.dabokadmin.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
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
    private val mealUseCases: MealUseCases,
    private val managerUseCases: ManagerUseCases,
    private val validateDate: ValidateDate = ValidateDate(),
    private val validateMenus: ValidateMenus = ValidateMenus()
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

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asSharedFlow()

    private val _successEvent = MutableSharedFlow<String>()
    val successEvent = _successEvent.asSharedFlow()

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

    fun loadMenusResult() {
        viewModelScope.launch {
            _menusState.update {
                it.copy(isLoading = true)
            }

            menuUseCases.getMenusUseCase(
                typeId = selectedCategory.id,
                lastId = menusState.value.lastId ?: -1
            ).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _menusState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Result.Loading -> {
                        _menusState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Result.Success -> {
                        result.data?.let { menus ->
                            _menusState.update {
                                it.copy(
                                    menus = menusState.value.menus + (menus.data ?: emptyList()).shuffled(),
                                    lastId = menus.data?.lastOrNull()?.id,
                                    hasNext = menus.hasNext
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun resetMenus() {
        _menusState.value = _menusState.value.copy(
            menus = listOf()
        )
    }

    fun registerMeal() {
        viewModelScope.launch {
            mealUseCases.registerMealUseCase(
                date = registerState.date,
                menuIds = registerState.menus.map { it.id }
            ).collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        _registerSuccess.value = true
                        _successEvent.emit("식단이 등록되었습니다.")
                    }
                }
            }
        }
    }

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.DateChanged -> {
                registerState = registerState.copy(
                    date = event.date,
                    dateError = null
                )
            }
            is RegisterFormEvent.MenuChanged -> {
                onSelectedMenu(event.menu)
                registerState = registerState.copy(
                    menus = selectedMenus,
                    menusError = null
                )
            }
            is RegisterFormEvent.Submit -> {
                validationCheck()
            }
        }
    }

    private fun validationCheck() {
        val dateResult = validateDate.execute(registerState.date)
        val menusResult = validateMenus.execute(registerState.menus)

        val hasError = listOf(
            dateResult,
            menusResult
        ).any { !it.successful }

        if (hasError) {
            registerState = registerState.copy(
                dateError = dateResult.errorMessage,
                menusError = menusResult.errorMessage
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
        loadMenusResult()
    }

    private fun onSelectedMenu(menu: Menu) {
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