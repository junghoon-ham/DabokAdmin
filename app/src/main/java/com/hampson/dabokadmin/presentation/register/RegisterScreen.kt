package com.hampson.dabokadmin.presentation.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.presentation.components.DefaultTopBar
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

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
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 24.dp)
                    ) {
                        DateComponent(viewModel = viewModel, state = state)
                        Spacer(modifier = Modifier.height(16.dp))

                        IngredientsComponent(viewModel = viewModel, state = state)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateComponent(
    viewModel: RegisterViewModel,
    state: RegisterFormState
) {
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 2.dp,
                    color = if (state.dateError != null) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp)
                .widthIn(min = 130.dp)
        ) {
            Text(
                text = state.date.ifEmpty { stringResource(id = R.string.input_date) },
                style = TextStyle(fontSize = 14.sp),
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = {
            calendarState.show()
        }) {
            Text(text = stringResource(id = R.string.select_date))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun IngredientsComponent(
    viewModel: RegisterViewModel,
    state: RegisterFormState
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen = false
            },
        ) {
            val scrollState = rememberScrollState()
            val searchText by viewModel.searchText.collectAsState()
            val categories by viewModel.categories.collectAsState()
            val isSearching by viewModel.isSearching.collectAsState()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
            ) {
                TextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Search") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                categories.forEach { category ->
                    Text(
                        text = category.name ?: "",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        category.ingredients?.forEach {  ingredient ->
                            var selected by remember { mutableStateOf(ingredient.selected) }

                            FilterChip(
                                selected = selected,
                                onClick = {
                                    ingredient.selected = !selected
                                    selected = !selected
                                },
                                label = { Text(text = ingredient.name ?: "") },
                                leadingIcon = if (selected) {
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = null,
                                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                                        )
                                    }
                                } else {
                                    null
                                }
                            )

                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    Button(onClick = {
        isSheetOpen = true
    }) {
        Text(text = stringResource(id = R.string.add_menu))
    }
}