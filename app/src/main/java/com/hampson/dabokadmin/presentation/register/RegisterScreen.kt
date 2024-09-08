package com.hampson.dabokadmin.presentation.register

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    date: String?,
    userActionType: UserActionType
) {
    val viewModel = hiltViewModel<RegisterViewModel>()
    val registerState = viewModel.registerState
    val categoriesState by viewModel.categoriesState.collectAsState()
    val menusState by viewModel.menusState.collectAsState()

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val activity = context.findActivity()

    var openAlertDialog by remember { mutableStateOf(false) }

    val success by viewModel.registerSuccess.collectAsState(false)

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is RegisterViewModel.ValidationEvent.Success -> {
                    openAlertDialog = true
                }
            }
        }
    }

    LaunchedEffect(key1 = success) {
        viewModel.successEvent.collect {  message ->
            navController.navigateUp()

            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(key1 = date) {
        if (!date.isNullOrEmpty()) {
            viewModel.loadMealResult(
                date = date,
                userActionType = userActionType
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(userActionType.getAppBarTitle(context))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                actions = {}
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
                        .padding(start = 16.dp, end = 16.dp)
                        .verticalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    DateComponent(
                        viewModel = viewModel,
                        registerState = registerState,
                        userActionType
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    MenuComponent(
                        viewModel = viewModel,
                        categoryState = categoriesState,
                        menusState = menusState,
                        registerState = registerState
                    )

                    Spacer(modifier = Modifier.height(86.dp))
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 22.dp, start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.onEvent(RegisterFormEvent.Submit)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp),
                            text = userActionType.getActionTitle(context)
                        )
                    }
                }

                AlertDialogRegister(
                    openAlertDialog = openAlertDialog,
                    viewModel = viewModel,
                    onClose = { openAlertDialog = false },
                    onConfirm = {
                        openAlertDialog = false

                        when (userActionType) {
                            UserActionType.REGISTER -> viewModel.registerMeal()
                            UserActionType.UPDATE -> viewModel.updateMeal()
                        }
                    },
                    userActionType = userActionType,
                    context = context
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateComponent(
    viewModel: RegisterViewModel,
    registerState: RegisterFormState,
    userActionType: UserActionType
) {
    val calendarState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH
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

        Button(
            onClick = { calendarState.show() },
            enabled = userActionType != UserActionType.UPDATE
        ) {
            Text(text = stringResource(id = R.string.select_date))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MenuComponent(
    viewModel: RegisterViewModel,
    categoryState: CategoryFormState,
    menusState: MenuFormState,
    registerState: RegisterFormState
) {
    var isOpenDialog by remember { mutableStateOf(false) }

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    if (isOpenDialog) {
        // 메뉴 추가 Dialog
        AlertDialog(
            onDismissRequest = {
                isOpenDialog = false
                viewModel.onSearchTextChange(null)
                viewModel.resetMenus()
            },
            title = { Text(viewModel.selectedCategory.label) },
            text = {
                val scrollState = rememberScrollState()
                var searchActive by remember { mutableStateOf(false) }

                LaunchedEffect(scrollState.maxValue) {
                    snapshotFlow { scrollState.value }
                        .collect { value ->
                            if (value == scrollState.maxValue && menusState.hasNext == true) {
                                viewModel.loadMenusResult()
                            }
                        }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        query = searchText ?: "",
                        onQueryChange = viewModel::onSearchTextChange,
                        onSearch = { searchActive = false },
                        active = false,
                        onActiveChange = { searchActive = false },
                        placeholder = { Text(text = stringResource(id = R.string.search_menu)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        },
                    ) {

                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    when {
                        menusState.menus.isEmpty() -> {
                            Text(
                                text = stringResource(id = R.string.empty_menus),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 32.dp, bottom = 32.dp),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        else -> {
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
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
                                    menusState.menus.forEach { menu ->
                                        val isSelected = viewModel.selectedMenus.any { it.id == menu.id }

                                        FilterChip(
                                            selected = isSelected,
                                            onClick = {
                                                viewModel.onEvent(RegisterFormEvent.MenuChanged(menu))
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
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isOpenDialog = false
                        viewModel.onSearchTextChange(null)
                        viewModel.resetMenus()
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
            .border(
                width = 2.dp,
                color = if (registerState.menusError != null) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primaryContainer
                },
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = stringResource(id = R.string.selected_menu),
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
                        viewModel.onEvent(RegisterFormEvent.MenuChanged(menu))
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


    Text(
        text = stringResource(id = R.string.category),
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 8.dp)
    )


    // 카테고리 리스트
    FlowRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        categoryState.categories.forEach { category ->
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

@Composable
fun AlertDialogRegister(
    openAlertDialog: Boolean,
    onClose: () -> Unit,
    onConfirm: () -> Unit,
    viewModel: RegisterViewModel,
    userActionType: UserActionType,
    context: Context
) {
    if (openAlertDialog) {
        AlertDialog(
            onDismissRequest = { onClose() },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text(text = stringResource(id = R.string.register))
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { onClose() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
            },
            title = {
                Text(
                    text = userActionType.getCheckActionMessage(context),
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "날짜 : ${viewModel.registerState.date}",
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val menuNames = viewModel.registerState.menus.joinToString(separator = ", ") { it.name }
                    Text(
                        text = "메뉴 : $menuNames",
                        fontSize = 14.sp
                    )
                }
            }
        )
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}