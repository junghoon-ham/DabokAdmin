package com.hampson.dabokadmin.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.domain.use_case.menu.MenuUseCases
import com.hampson.dabokadmin.util.Constants.SPLASH_HOLDING_TIME
import com.hampson.dabokadmin.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val menuUseCases: MenuUseCases
) : ViewModel() {

    var splashScreenDelay by mutableStateOf(true)

    private val _menuState = MutableStateFlow(MenuState())
    val menuState = _menuState.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            delay(SPLASH_HOLDING_TIME)
            splashScreenDelay = false
        }

        _menuState.update {
            it.copy(menuId = -1)
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            loadMenuResult()
        }
    }

    private fun loadMenuResult() {
        viewModelScope.launch {
            menuUseCases.getMenuUseCase(
                menuId = menuState.value.menuId
            ).collect {result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> {
                        _menuState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Result.Success -> {
                        result.data?.let { menu ->
                            _menuState.update {
                                it.copy(
                                    menu = menu
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}