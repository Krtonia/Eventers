package com.example.eventer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.Routes
import com.example.eventer.R
import com.example.eventer.ui.theme.firasans
import kotlinx.coroutines.coroutineScope

@Composable
fun Welcome(navController: NavController){
    //val Start() = remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxSize(1f)
        .background(Color.Black),
    horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        )
    {
        Text(text = "Eventers",
             fontSize = 68.sp,
             color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = firasans)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "''Lets Create Memories''",
            color = Color.White,
            fontSize = 19.sp,
            fontStyle = FontStyle.Italic

        )

        Spacer(modifier = Modifier.height(0.dp))

        Image(modifier = Modifier.size(450.dp),painter = painterResource(id = R.drawable.welcome),
            contentDescription ="Home page Image" )

        Spacer(modifier = Modifier.height(0.dp))

        Button(onClick = { navController.navigate(Routes.second) },
        modifier = Modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        )
    {
        Text(text = "Let's Go",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold)
    }
    }
}