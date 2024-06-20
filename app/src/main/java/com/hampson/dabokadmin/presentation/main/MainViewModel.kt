package com.hampson.dabokadmin.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.util.Constants.SPLASH_HOLDING_TIME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var splashScreenDelay by mutableStateOf(true)

    init {
        viewModelScope.launch {
            delay(SPLASH_HOLDING_TIME)
            splashScreenDelay = false
        }
    }
}