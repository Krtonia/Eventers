package com.io.eventer.ui.home.options

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.io.eventer.R
import com.io.eventer.navigation.Routes
import com.io.eventer.ui.theme.firasans
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.io.eventer.ui.home.event.viewmodel.EventViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Options(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Options",
                        style = MaterialTheme.typography.headlineLarge,
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
    BackHandler {
        navController.navigate(Routes.fourth) {
            popUpTo(0                                                       ) {
                inclusive = true
            }
        }
    }
}

@Composable
fun Sed(navController: NavController) {
    val context = LocalContext.current
    val viewmodel: EventViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .padding(16.dp).size(150.dp),
            painter = painterResource(id = R.drawable.options),
            contentDescription = "options"
        )

        // Google Calendar Launch
        OptionCard(
            imageRes = R.drawable.calender,
            text = "Open Calendar",
            onClick = {
                val googleCalendarPackage = "com.google.android.calendar"
                val intent = context.packageManager.getLaunchIntentForPackage(googleCalendarPackage)

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

        // Logout
        OptionCard(
            imageRes = R.drawable.logout,
            text = "Logout",
            onClick = { viewmodel.signOut(navController) }
        )

        // Help
        OptionCard(
            imageRes = R.drawable.help,
            text = "Help & Support",
            onClick = { navController.navigate(Routes.eigth) }
        )

        // About
        OptionCard(
            imageRes = R.drawable.info,
            text = "About",
            onClick = { navController.navigate(Routes.seventh) }
        )
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp),
                painter = painterResource(id = imageRes),
                contentDescription = text
            )

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                fontFamily = firasans
            )

        }
    }
}

@Composable
fun Help(navController: NavController) {
    val context = LocalContext.current

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.support))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(350.dp)
                    .padding(top = 0.dp)
            )

            Text(
                text = "Need Help?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontFamily = firasans
            )

            Text(
                text = "Eventers is a personal project developed to showcase modern Android development practices. If you notice a bug, have a feature suggestion, or just want to give feedback, feel free to open an issue on the GitHub repository. Your input is always appreciated!",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = firasans,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Medium
            )

            Button(
                onClick = {
                    val issueUrl = "https://github.com/Krtonia/Eventers/issues/new"
                    val intent = Intent(Intent.ACTION_VIEW, issueUrl.toUri())
                    context.startActivity(intent)
                }
            ) {
                Text(
                    text = "Open GitHub Issue",
                    fontFamily = firasans,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}