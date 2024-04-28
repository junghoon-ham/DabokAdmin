package com.hampson.dabokadmin.presentation.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hampson.dabokadmin.R
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel = hiltViewModel<RegisterViewModel>()
    val registerState = viewModel.registerState
    val categoriesState = viewModel.categoriesState.collectAsState()
    val menusState = viewModel.menusState.collectAsState()

    val scrollState = rememberScrollState()
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
            .verticalScroll(scrollState)
    ) {
        DateComponent(viewModel = viewModel, registerState = registerState)
        Spacer(modifier = Modifier.height(16.dp))
        MenuComponent(
            viewModel = viewModel,
            categoryState = categoriesState,
            menusState = menusState
        )



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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateComponent(
    viewModel: RegisterViewModel,
    registerState: RegisterFormState
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
                    color = if (registerState.dateError != null) {
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
                text = registerState.date.ifEmpty { stringResource(id = R.string.input_date) },
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MenuComponent(
    viewModel: RegisterViewModel,
    categoryState: State<CategoryFormState>,
    menusState: State<MenuFormState>
) {
    var isOpenDialog by remember { mutableStateOf(false) }

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    if (isOpenDialog) {
        // 메뉴 추가 Dialog
        AlertDialog(
            onDismissRequest = {
                isOpenDialog = false
                viewModel.onSearchTextChange("")
            },
            title = { Text(viewModel.selectedCategory.label) },
            text = {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = viewModel::onSearchTextChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = stringResource(id = R.string.search_menu)) }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isSearching) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        } else {
                            menusState.value.menus.forEach { menu ->
                                val isSelected = viewModel.selectedMenus.contains(menu)

                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        viewModel.onSelectedMenu(menu)
                                    },
                                    label = { Text(text = menu.name) },
                                    leadingIcon = if (isSelected) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Done,
                                                contentDescription = null,
                                                modifier = Modifier.size(
                                                    FilterChipDefaults.IconSize)
                                            )
                                        }
                                    } else {
                                        null
                                    }
                                )

                                Spacer(modifier = Modifier.width(6.dp))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isOpenDialog = false
                        viewModel.onSearchTextChange("")
                    }
                ) {
                    Text(stringResource(id = R.string.ok))
                }
            }
        )
    }

    // 선택한 메뉴
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.input_menu),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
        ) {
            viewModel.selectedMenus.forEach { menu ->
                val isSelected = viewModel.selectedMenus.contains(menu)

                FilterChip(
                    selected = isSelected,
                    onClick = {
                        viewModel.onSelectedMenu(menu)
                    },
                    label = { Text(text = menu.name) },
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = null,
                                modifier = Modifier.size(
                                    FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    }
                )

                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }

    Spacer(modifier = Modifier.width(16.dp))

    Text(
        text = stringResource(id = R.string.category),
        style = MaterialTheme.typography.labelMedium
    )

    // 카테고리 리스트
    FlowRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        categoryState.value.categories.forEach { category ->
            FilterChip(
                selected = false,
                onClick = {
                    isOpenDialog = true
                    viewModel.onSelectedCategory(category)
                },
                label = { Text(text = category.label) }
            )

            Spacer(modifier = Modifier.width(6.dp))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}