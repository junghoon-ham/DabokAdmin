package com.hampson.dabokadmin.presentation.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hampson.dabokadmin.ui.theme.DabokAdminTheme

@Composable
fun RegisterScreen(navController: NavController) {
    DabokAdminTheme {
        Scaffold(
            content = {
                Box(
                    modifier = Modifier.fillMaxSize().padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Register",
                        fontFamily = FontFamily.Serif,
                        fontSize = 22.sp
                    )
                }
            }
        )
    }
}