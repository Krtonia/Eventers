package com.io.eventer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.io.eventer.navigation.Routes
import com.io.eventer.R
import com.io.eventer.ui.auth.viewmodel.AuthViewModel
import com.io.eventer.ui.theme.firasans
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Welcome(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            delay(1000)
            try {
                val isLoggedIn = viewModel.isUserLoggedIn()
                android.util.Log.d("Welcome", "User logged in: $isLoggedIn")
                if (isLoggedIn) {
                    navController.navigate(Routes.fourth) {
                        popUpTo(Routes.first) { inclusive = true }
                    }
                } else {
                    navController.navigate(Routes.second) {
                        popUpTo(Routes.first) { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("Welcome", "Error checking login status", e)
                navController.navigate(Routes.second) {
                    popUpTo(Routes.first) { inclusive = true }
                }
            }
        }
    }

    // LaunchedEffect to automatically navigate after 1 second
//    LaunchedEffect(key1 = true) {
//        delay(300)
//        navController.navigate(Routes.second) {
//            popUpTo(navController.graph.startDestinationId) {
//                inclusive = true
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Eventers",
            fontSize = 68.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = firasans
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "''Lets Create Memories''",
            color = Color.White,
            fontSize = 19.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(0.dp))

        Image(
            modifier = Modifier.size(450.dp),
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "Home page Image"
        )
    }
}