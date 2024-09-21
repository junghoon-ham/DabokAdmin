package com.hampson.dabokadmin.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.domain.model.UserInfo
import com.hampson.dabokadmin.domain.use_case.user.UserInfoUseCases
import com.hampson.dabokadmin.util.Constants.SPLASH_HOLDING_TIME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userInfoUseCases: UserInfoUseCases
) : ViewModel() {

    var splashScreenDelay by mutableStateOf(true)

    private val _hasUserInfo = MutableStateFlow<Boolean?>(null)
    val hasUserInfo = _hasUserInfo.asStateFlow()

    init {
        viewModelScope.launch {
            delay(SPLASH_HOLDING_TIME)
            splashScreenDelay = false
        }

        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            userInfoUseCases.getUserInfoUseCase()
                .collect { result ->
                    _hasUserInfo.update { result.userEmail.isNotEmpty() }
                }
        }
    }
}