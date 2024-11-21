package com.example.eventer.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.Routes
import com.example.eventer.R
import com.example.eventer.ui.theme.firasans
import java.io.FileDescriptor
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Options(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(110.dp)
                    .padding(),
                title = {
                    Text(
                        text = "Options",
                        fontSize = 42.sp,
                        fontFamily = firasans,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                NavigationBarItem(
                    selected = currentRoute == Routes.third,
                    colors = NavigationBarItemDefaults.colors(Color(0xFF0047AB)),
                    onClick = { navController.navigate(Routes.third) },
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                    label = { Text(text = "Home") }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.fourth,
                    colors = NavigationBarItemDefaults.colors(Color(0xFF0047AB)),
                    onClick = { navController.navigate(Routes.fourth) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text(text = "Profile") }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.fifth,
                    colors = NavigationBarItemDefaults.colors(Color(0xFF0047AB)),
                    onClick = { navController.navigate(Routes.fifth) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Options"
                        )
                    },
                    label = { Text(text = "Options") }
                )
            }
        },
    ) {
        Sed(navController)
    }
}

@Composable
fun Sed(navController: NavController) {

    val context = LocalContext.current

    Card(
        onClick = {

            val googleCalendarPackage = "com.google.android.calendar"
            val intent = context.packageManager.getLaunchIntentForPackage(googleCalendarPackage)

            if (intent != null) {
                // Calendar app is installed
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else {
                // Calendar app not installed, open in browser
                val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = android.net.Uri.parse("https://calendar.google.com")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(browserIntent)
            }
        },
        modifier = Modifier.padding(top = 140.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        ) {
            Image(
                modifier = Modifier.padding(start = 10.dp),
                painter = painterResource(id = R.drawable.calender),
                contentScale = ContentScale.Crop,
                contentDescription = "Calendar"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                contentAlignment = Alignment.BottomCenter
            )
            {
                Text(
                    text = "Calendar",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontFamily = firasans,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
    }

    Card(
        onClick = { /* TODO: Card action */ },
        //modifier=Modifier.fillMaxSize(),
        modifier = Modifier
            .padding(top = 140.dp)
            .padding(start = 220.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        ) {
            Image(
                modifier = Modifier.padding(start = 30.dp),
                painter = painterResource(id = R.drawable.logout),
                contentScale = ContentScale.Crop,
                contentDescription = "Log Out"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                contentAlignment = Alignment.BottomCenter
            )
            {
                Text(
                    text = "Log Out",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontFamily = firasans,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
    }

    Card(
        onClick = { navController.navigate(Routes.seventh) },
        //modifier=Modifier.fillMaxSize(),
        modifier = Modifier
            .padding(top = 130.dp)
            .padding(top = 250.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        ) {
            Image(
                modifier = Modifier.padding(start = 10.dp),
                painter = painterResource(id = R.drawable.help),
                contentScale = ContentScale.Crop,
                contentDescription = "Help"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp),
                contentAlignment = Alignment.BottomCenter
            )
            {
                Text(
                    text = "Help",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontFamily = firasans,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
    }

    Card(
        onClick = { navController.navigate(Routes.sixth) },
        modifier = Modifier
            .padding(top = 380.dp)
            .padding(start = 220.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        ) {
            Image(
                modifier = Modifier.padding(start = 10.dp),
                painter = painterResource(id = R.drawable.info),
                contentScale = ContentScale.Crop,
                contentDescription = "About"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                contentAlignment = Alignment.BottomCenter
            )
            {
                Text(
                    text = "About us",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontFamily = firasans,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
    }
}

@Composable
fun Help(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(modifier = Modifier.padding(top = 100.dp),
            onClick = { /*TODO*/ }) {
            Text(
                text = "Support",
                fontFamily = firasans,
                fontSize = 28.sp
            )
        }
        Text(
            text = "For Support or Help click on the Support button",
            fontSize = 18.sp,
            fontFamily = firasans,
            fontWeight = FontWeight.Medium
        )
    }
}














