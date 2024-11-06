package com.example.eventer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Routes
import com.example.eventer.NavItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Profile(navController: NavController){

    /*val nl = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Profile", Icons.Default.Person),
        NavItem("Options", Icons.Default.List)
    )*/

    /*var selindex by remember { mutableStateOf(0)}

    when(selindex){
        0 -> Home(navController)
        1 -> Profile(navController)
        2 -> Options(navController)
    }*/

    //var selindex by remember { mutableStateOf(0)}

    Scaffold(
        bottomBar = {
            NavigationBar() {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.third) }, icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text(text = "Home") })

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.fourth) }, icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text(text = "Profile") })

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.fifth) }, icon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Options"
                        )
                    },
                    label = { Text(text = "Options") })
            }
         },
    ) {
        Column(modifier = Modifier.background(Color(0xFF82C8E5)).fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "Profile Screen",
                fontSize = 40.sp,
                color = Color.Black)
        }
    }
}