package com.example.eventer.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.example.eventer.R
import com.Routes
import com.example.eventer.ui.theme.firasans
import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController) {
    var cardCount by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val cardList = remember { mutableStateListOf<String>() }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        topBar = { TopAppBarContent() },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
    ) {
        // Passing cardList and cardCount to Card composable
        Card(cardList = cardList)
        if (showDialog) {
            EventDialog(
                onDismiss = { showDialog = false },
                onConfirm = { cardCount++;cardList.add("Card $cardCount") }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = (Color(0xFFF2F1F6))) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
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
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile") },
            label = { Text(text = "Profile") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.fifth,
            colors = NavigationBarItemDefaults.colors(Color(0xFF0047AB)),
            onClick = { navController.navigate(Routes.fifth) },
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = "Options") },
            label = { Text(text = "Options") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent() {
    TopAppBar(
        modifier = Modifier
            .height(110.dp)
            .padding(),
        title = {
            Text(
                text = "Welcome",
                fontSize = 42.sp,
                fontFamily = firasans,
                fontWeight = FontWeight.SemiBold
            )
        }
    )
}

@Composable
fun EventDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) { Text(text = "Add") }
        },
        title = {
            Text(
                text = "Create an Event",
                modifier = Modifier.padding(5.dp),
                fontFamily = firasans,
                fontWeight = FontWeight.SemiBold
            )
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text(text = "Cancel") }
        }
    )
}

/*@Composable
fun ContentCards() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        repeat(3) {
            ElevatedCard(
                onClick = { /* TODO: Card action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .height(200.dp)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(id = R.drawable.party),
                    contentDescription = "Home page Image"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "''Text Here''",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
        }
    }
}*/

@Composable
fun Card(cardList: List<String>) {
    Column(
        modifier = Modifier
            .padding(top = 76.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(9.dp))

        // Display the list of cards
        LazyColumn {
            items(cardList) {
                ElevatedCard(
                    onClick = { /* TODO: Card action */ },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .height(200.dp),
                    elevation = CardDefaults.cardElevation(5.dp),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(id = R.drawable.party),
                        contentDescription = "Home page Image"
                    )
                    Text(text = "cardText", fontSize = 24.sp, color = Color.Black)
                    Text(text = "This is a dynamically added card.", fontSize = 16.sp)
                }
            }
        }
    }
}

/*@Composable
fun Star()
{

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launch the image picker

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Permission launcher for Android 13+
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) imagePickerLauncher.launch("image/*")
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                imagePickerLauncher.launch("image/*")
            }
        }) {
            Text("Pick an Image")
        }

        imageUri?.let { uri ->
            val painter = rememberAsyncImagePainter(uri)
            Image(
                painter = painter,
                contentDescription = "Selected Image",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}*/