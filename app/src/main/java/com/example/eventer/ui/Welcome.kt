package com.example.eventer.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventer.navigation.Routes
import com.example.eventer.R
import com.example.eventer.ui.theme.firasans
import kotlinx.coroutines.delay

@Composable
fun Welcome(navController: NavController) {
    // LaunchedEffect to automatically navigate after 1 second
    LaunchedEffect(key1 = true) {
        delay(300) // 1 second delay
        navController.navigate(Routes.second) {
            // Optional: Clear the back stack so user can't return to welcome screen
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

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