package com.hampson.dabokadmin.presentation.sign_in

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.presentation.navigation.Route
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<SignInVIewModel>()
    val state = viewModel.state
    val context = LocalContext.current as? Activity

    var openAlertDialogFailureAuth by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        context?.finish()
    }

    LaunchedEffect(key1 = context) {
        launch {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is SignInVIewModel.ValidationEvent.Success -> {

                    }
                }
            }
        }

        launch {
            viewModel.authenticationEvent.collect { event ->
                when (event) {
                    is SignInVIewModel.AuthenticationEvent.Success -> {
                        navController.popBackStack()
                        navController.navigate(route = Route.MAIN_SCREEN)
                    }
                    is SignInVIewModel.AuthenticationEvent.Failure -> {
                        openAlertDialogFailureAuth = true
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(stringResource(id = R.string.login))
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = state.email,
                        onValueChange = { email ->
                            viewModel.onEvent(SignInFormEvent.EmailChanged(email))
                        },
                        isError = state.emailError != null,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "Email")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )
                    if (state.emailError != null) {
                        Text(
                            text = state.emailError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = state.password,
                        onValueChange = { password ->
                            viewModel.onEvent(SignInFormEvent.PasswordChanged(password))
                        },
                        isError = state.passwordError != null,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "Password")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            viewModel.onEvent(SignInFormEvent.Submit)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.login))
                    }
                }
            }

            AlertDialogFailureAuth(
                openAlertDialog = openAlertDialogFailureAuth,
                onClose = { openAlertDialogFailureAuth = false },
                onConfirm = { openAlertDialogFailureAuth = false }
            )
        }
    )
}

@Composable
private fun AlertDialogFailureAuth(
    openAlertDialog: Boolean,
    onClose: () -> Unit,
    onConfirm: () -> Unit
) {
    if (openAlertDialog) {
        AlertDialog(
            onDismissRequest = { onClose() },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
            },
            title = {},
            text = { Text(text = stringResource(id = R.string.failure_user_info)) }
        )
    }
}