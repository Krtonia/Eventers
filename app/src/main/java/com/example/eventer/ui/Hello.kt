package com.example.eventer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Routes
import com.example.eventer.R
import com.example.eventer.ui.theme.firasans

fun <T> List(size: Unit, init: Unit) {

}

@Composable
fun Hello(navController: NavController) {
    val gc = listOf(
        Color(0xFF353E43),
        Color(0xFF252525),
        Color(0xFF6A5ACD)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = gc
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier.size(350.dp),
            painter = painterResource(id = R.drawable.model_fg),
            contentDescription = "Login image"
        )

        Text(
            modifier = Modifier,
            text = "Welcome to Eventers",
            fontFamily = firasans,
            color = Color.White,
            fontStyle = FontStyle.Italic,
            fontSize = 38.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Right
        )

        Text(
            text = "Login to your Account",
            color = Color.White,
            fontFamily = firasans,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(25.dp))

        val name = remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = name.value,
            shape = RoundedCornerShape(25.dp),
            textStyle = TextStyle(color = Color.White),
            onValueChange = { name.value = it },
            placeholder = { Text(text = "Email", lineHeight = 15.sp, color = Color.White) },
        )

        Spacer(modifier = Modifier.height(15.dp))

        val password = remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = password.value,
            shape = RoundedCornerShape(25.dp),
            textStyle = TextStyle(color = Color.White),
            onValueChange = { password.value = it },
            placeholder = { Text(text = "Password", lineHeight = 15.sp, color = Color.White) })

        Spacer(modifier = Modifier.height(20.dp))

        Button(modifier = Modifier
            .width(180.dp)
            .height(50.dp),
            onClick = { navController.navigate(Routes.third) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF82C8E5))) {
            Text(
                text = "Login",
                fontFamily = firasans,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = { }, modifier = Modifier.height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Forgot password",
                fontFamily = firasans,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "OR",
            fontFamily = firasans,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 17.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Signup With",
            color = Color.Black,
            fontFamily = firasans,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clickable { },
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google"
            )

            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clickable { },
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Facebook"
            )

            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clickable { },
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = "Twitter"
            )

            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clickable { },
                painter = painterResource(id = R.drawable.email),
                contentDescription = "Email"
            )
        }

    }
}