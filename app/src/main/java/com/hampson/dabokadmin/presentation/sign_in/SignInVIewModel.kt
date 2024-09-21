package com.hampson.dabokadmin.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.dabokadmin.domain.use_case.user.UserInfoUseCases
import com.hampson.dabokadmin.domain.use_case.validation.ValidateEmail
import com.hampson.dabokadmin.domain.use_case.validation.ValidatePassword
import com.hampson.dabokadmin.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInVIewModel @Inject constructor(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val userInfoUseCases: UserInfoUseCases
): ViewModel() {

    var state by mutableStateOf(SignInFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val authenticationEventChannel = Channel<AuthenticationEvent>()
    val authenticationEvent = authenticationEventChannel.receiveAsFlow()

    fun onEvent(event: SignInFormEvent) {
        when (event) {
            is SignInFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is SignInFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is SignInFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        state = state.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage
        )

        if (hasError) return

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)

            if (checkUserInfo()) {
                authenticationEventChannel.send(AuthenticationEvent.Success)
                saveUserInfo()
            } else {
                authenticationEventChannel.send(AuthenticationEvent.Failure)
            }
        }
    }

    private fun checkUserInfo(): Boolean {
        val passEmail = Constants.ADMIN_EMAIL == state.email
        val passPassword = Constants.ADMIN_PASSWORD == state.password

        return passEmail && passPassword
    }

    private suspend fun saveUserInfo() {
        userInfoUseCases.saveUserInfoUseCase(email = state.email)
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    sealed class AuthenticationEvent {
        object Success : AuthenticationEvent()
        object Failure : AuthenticationEvent()
    }
}