package com.hampson.dabokadmin.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.presentation.components.DefaultTextComponent
import com.hampson.dabokadmin.presentation.components.DefaultTopBar
import com.hampson.dabokadmin.presentation.components.HeadingTextComponent
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme
import kotlinx.coroutines.flow.collect

@Composable
fun RegisterScreen(navController: NavController) {
    DabokAdminTheme {
        Scaffold(
            topBar = {
                DefaultTopBar(
                    title = stringResource(id = R.string.register_menu),
                    navController = navController
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            content = {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    val viewModel = hiltViewModel<RegisterViewModel>()
                    val state = viewModel.state
                    val context = LocalContext.current
                    LaunchedEffect(key1 = context) {
                        viewModel.validationEvents.collect { event ->
                            when (event) {
                                is RegisterViewModel.ValidationEvent.Success -> {
                                    Toast.makeText(
                                        context,
                                        "성공",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        var filledText by remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = filledText,
                            onValueChange = { filledText = it },
                            readOnly = false,
                            textStyle = TextStyle(textAlign = TextAlign.Left),
                            label = {
                                Text(text = "메뉴를 입력하세요.")
                            },
                            placeholder = {
                                Text(text = "")
                            },
                            prefix = {
                                Text(text = "")
                            },
                            suffix = {
                                 Text(text = "")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.MonitorWeight,
                                    contentDescription = null
                                )
                            },
                            supportingText = {
                                Text(text = "TEST")
                            },
                            isError = false,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    println("Hello world")
                                }
                            )
                        )
                        Spacer(modifier = Modifier.height(132.dp))
                    }
                }
            }
        )
    }
}