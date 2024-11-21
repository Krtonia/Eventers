package com.example.eventer.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventer.R
import com.example.eventer.ui.theme.firasans

@Composable
fun Info(navController: NavController) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        Image(imageVector = Icons.Default.Info, contentDescription = "info")
        Text(
            text = "This Application is developed by a team member Shikhar(Krtonia) for a college Project the team includes ''Raj Raghav Singh'' , ''Shikhar Tiwari'' and ''Rishabh Rai'' the application includes a basic functionality of creating an Event and Organizing the event, application also consist of a mainframe website and Database for Eventers",
            fontFamily = firasans,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {

        Image(
            modifier = Modifier.padding(bottom = 400.dp), imageVector = Icons.Default.Person,
            contentDescription = "1 Raj Raghav Singh"
        )
        TextButton(modifier = Modifier.padding(bottom = 400.dp, start = 20.dp),
            onClick = {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/raghavsingh05"))
                context.startActivity(intent)
            }) {
            Text(
                text = "Raj Raghav Singh",
                color = Color.Black,
                fontFamily = firasans,
                fontSize = 18.sp
            )
        }

        Image(
            modifier = Modifier.padding(bottom = 320.dp), imageVector = Icons.Default.Person,
            contentDescription = "2 Shikhar Tiwari"
        )
        TextButton(modifier = Modifier.padding(bottom = 320.dp, start = 20.dp),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/krtonia"))
                context.startActivity(intent)
            }) {
            Text(
                text = "Shikhar Tiwari",
                color = Color.Black,
                fontFamily = firasans,
                fontSize = 18.sp
            )
        }

        Image(
            modifier = Modifier.padding(bottom = 240.dp), imageVector = Icons.Default.Person,
            contentDescription = "3 Rishabh Rai"
        )
        TextButton(modifier = Modifier.padding(bottom = 240.dp, start = 20.dp),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/rishabhrai5"))
                context.startActivity(intent)
            }) {
            Text(
                text = "Rishabh Rai",
                color = Color.Black,
                fontFamily = firasans,
                fontSize = 18.sp
            )
        }

    }
}