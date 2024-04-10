package com.hampson.dabokadmin.presentation.register

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.presentation.components.DefaultTextComponent
import com.hampson.dabokadmin.presentation.components.DefaultTopBar
import com.hampson.dabokadmin.presentation.components.HeadingTextComponent
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.flow.collect
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
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
            modifier = Modifier.fillMaxSize(),
            content = {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    val viewModel = hiltViewModel<RegisterViewModel>()
                    val state = viewModel.state
                    val context = LocalContext.current

                    val calendarState = rememberSheetState()

                    CalendarDialog(
                        state = calendarState,
                        config = CalendarConfig(
                            monthSelection = true,
                            yearSelection = true,
                            style = CalendarStyle.WEEK
                        ),
                        selection = CalendarSelection.Date { date ->
                            viewModel.onEvent(RegisterFormEvent.DateChanged(date.toString()))
                        }
                    )

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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(10.dp)
                                    ) // 배경색과 둥근 모서리를 설정
                                    .border(
                                        width = 2.dp,
                                        color = if (state.dateError != null) {
                                            MaterialTheme.colorScheme.error
                                        } else {
                                            MaterialTheme.colorScheme.primaryContainer
                                        },
                                        shape = RoundedCornerShape(10.dp)
                                    ) // 테두리 색과 둥근 모서리를 설정
                                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 24.dp) // 내부 여백을 설정
                                    .widthIn(min = 130.dp)
                            ) {
                                Text(
                                    text = state.date.ifEmpty { stringResource(id = R.string.input_date) }, // 표시할 텍스트
                                    style = TextStyle(fontSize = 14.sp), // 텍스트 스타일 설정
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(onClick = {
                                calendarState.show()
                            }) {
                                Text(text = "날짜 선택")
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                }
                        ) {
                            TextField(
                                value = state.date,
                                onValueChange = { date ->
                                    viewModel.onEvent(RegisterFormEvent.DateChanged(date))
                                },
                                readOnly = true,
                                modifier = Modifier.fillMaxWidth(),
                                isError = state.dateError != null,
                                placeholder = {
                                    Text(text = "날짜를 선택해 주세요.")
                                }
                            )
                            if (state.dateError != null) {
                                Text(
                                    text = state.dateError,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = state.ingredients.toString(),
                            onValueChange = { ingredients ->
                                viewModel.onEvent(RegisterFormEvent.DateChanged(ingredients))
                            },
                            isError = state.ingredientsError != null,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Ingredients")
                            }
                        )
                        if (state.ingredientsError != null) {
                            Text(
                                text = state.ingredientsError,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.onEvent(RegisterFormEvent.Submit)
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(text = "생성")
                        }
                    }
                }
            }
        )
    }
}