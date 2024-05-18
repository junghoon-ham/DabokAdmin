package com.hampson.dabokadmin.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.presentation.ManagerViewModel

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<ManagerViewModel>()
    val darkTheme by viewModel.darkTheme.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = stringResource(id = R.string.settings_theme),
                style = MaterialTheme.typography.labelLarge,
            )

            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                ThemeSwitcher(
                    darkTheme = darkTheme,
                    size = 50.dp,
                    padding = 5.dp,
                    onClick = { viewModel.updateAppTheme() }
                )
            }
        }
    }
}