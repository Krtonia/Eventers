package com.io.eventer.ui.home.options

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.io.eventer.R
import com.io.eventer.navigation.Routes
import com.io.eventer.ui.theme.firasans
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.io.eventer.ui.home.event.viewmodel.EventViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.auth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Options (navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
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
                    selected = currentRoute == Routes.fourth,
                    onClick = { navController.navigate(Routes.fourth) },
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                    label = { Text(text = "Home") }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.fifth,
                    onClick = { navController.navigate(Routes.fifth) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text(text = "Profile") }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.sixth,
                    onClick = { navController.navigate(Routes.sixth) },
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "Options"
                        )
                    },
                    label = { Text(text = "Options") }
                )
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Sed(navController)
            }
        }
    )
}

@Composable
fun Sed(navController: NavController) {
    val context = LocalContext.current
    val supabase : SupabaseClient
    val viewmodel : EventViewModel = hiltViewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OptionCard(
                modifier = Modifier.weight(1f),
                imageRes = R.drawable.calender,
                text = "",
                onClick = {
                    val googleCalendarPackage = "com.google.android.calendar"
                    val intent =
                        context.packageManager.getLaunchIntentForPackage(googleCalendarPackage)

                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    } else {
                        val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = "https://calendar.google.com".toUri()
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(browserIntent)
                    }
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Logout Card
            OptionCard(
                modifier = Modifier.weight(1f),
                imageRes = R.drawable.logout,
                text = "",
                onClick = { viewmodel.signOut(navController) }
            )
        }

        // Second Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 232.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Help Card
            OptionCard(
                modifier = Modifier.weight(1f),
                imageRes = R.drawable.help,
                text = "",
                onClick = { navController.navigate(Routes.seventh) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            // About Card
            OptionCard(
                modifier = Modifier.weight(1f),
                imageRes = R.drawable.info,
                text = "",
                onClick = { navController.navigate(Routes.sixth) }
            )
        }
    }
}

@Composable
fun OptionCard(
    modifier: Modifier = Modifier,
    imageRes: Int,
    text: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                painter = painterResource(id = imageRes),
                contentScale = ContentScale.Fit,
                contentDescription = text
            )
        }
    }
}

@Composable
fun Help(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.padding(top = 100.dp),
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