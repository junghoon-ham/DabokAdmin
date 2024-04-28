package com.hampson.dabokadmin.presentation.register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.presentation.components.DefaultTopBar
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

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
                            RegisterScreen(navController = navController)
                        }
                    }
                )
            }
        }
    }
}