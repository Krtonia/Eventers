package com.example.eventer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun <T> List(size: Unit, init: Unit) {

}

@Composable
fun Start() {
    val Colors = listOf(
        Color.Black,
        Color.Blue,
        Color.Magenta)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = Colors,
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier,
            text = "Welcome to Eventers\n \n ",
            color = Color.White,
            fontStyle = FontStyle.Italic,
            fontSize = 38.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Right
        )
        val name = remember { mutableStateOf(TextFieldValue(""))}
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            placeholder = { Text(text = "Login-ID", lineHeight = 15.sp)},
        )

        val password = remember { mutableStateOf(TextFieldValue(""))}
        TextField(value = password.value,
            onValueChange ={ password.value = it},
            placeholder = { Text(text = "Password",lineHeight = 15.sp)})
    }
}