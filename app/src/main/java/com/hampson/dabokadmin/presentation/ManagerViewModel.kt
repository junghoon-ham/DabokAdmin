package com.hampson.dabokadmin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.domain.use_case.manager.ManagerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerViewModel @Inject constructor(
    private val managerUseCase: ManagerUseCases
) : ViewModel() {

    private val _darkTheme = MutableStateFlow(false)
    val darkTheme = _darkTheme.asStateFlow()

    init {
        getAppTheme()
    }

    private fun getAppTheme() {
        viewModelScope.launch {
            managerUseCase.getThemeUseCase()
                .collect { result ->
                    _darkTheme.update { result }
                }
        }
    }

    fun updateAppTheme() {
        viewModelScope.launch {
            managerUseCase.updateThemeUseCase()
        }
    }
}