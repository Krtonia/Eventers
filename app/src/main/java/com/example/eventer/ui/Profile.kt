package com.example.eventer.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.Routes
import com.example.eventer.R
import com.example.eventer.ui.theme.firasans
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Profile(navController: NavController) {
    Scaffold(
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
        ProfileContent(navController)
    }
}

@Composable
fun ProfileContent(navController: NavController,) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    Het(onImageSelected = { uri ->
        selectedImageUri = uri
    },
        navController
    )
}

@Composable
fun Het(onImageSelected: (Uri) -> Unit,navController: NavController) {
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var userInput by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    val context = LocalContext.current

    val gc = listOf(
        Color(0xFF252525),
        Color(0xFFBE5103)
    )

    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        return File.createTempFile(
            imageFileName,
            ".jpg",
            context.externalCacheDir
        )
    }

    var tempImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempImageUri?.let { uri ->
                imageUri = uri
                onImageSelected(uri)
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            onImageSelected(it)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val file = createImageFile()
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
            tempImageUri = uri
            cameraLauncher.launch(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(color = Color(0xFFF2F1F6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(
            modifier = Modifier
                .height(250.dp)
                .background(brush = Brush.verticalGradient(colors = gc))
        ) {
            val (topImg, profile) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.arc_3),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(topImg) {
                        bottom.linkTo(parent.bottom)
                    }
            )

            Image(
                painter = if (imageUri != null) {
                    rememberAsyncImagePainter(imageUri)
                } else {
                    painterResource(id = R.drawable.test)
                },
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clickable { galleryLauncher.launch("image/*") }
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .constrainAs(profile) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(topImg.bottom)
                    }
            )
        }
    }

    Text(
        modifier = Modifier.padding(top = 275.dp, start = 50.dp),
        text = "Hello! What Brings You here?",
        fontSize = 25.sp,
        fontFamily = firasans,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )

    Image(
        modifier = Modifier.padding(top = 200.dp, start = 265.dp),
        painter = painterResource(id = R.drawable.edit),
        contentDescription = "Change Image"
    )

    Image(
        modifier = Modifier
            .padding(top = 350.dp, end = 200.dp, start = 40.dp)
            .size(110.dp)
            .clickable {navController.navigate(Routes.eigth) },
        painter = painterResource(id = R.drawable.profile),
        contentDescription = "profile icon"
    )

    Text(
        modifier = Modifier
            .padding(top = 393.dp, start = 170.dp)
            .clickable { navController.navigate(Routes.eigth)},
        text = "My Profile",
        fontSize = 20.sp,
        fontFamily = firasans,
        fontWeight = FontWeight.Medium
    )
    Icon(
        modifier = Modifier
            .padding(top = 385.dp, start = 330.dp)
            .size(40.dp)
            .clickable {navController.navigate(Routes.eigth) },
        imageVector = Icons.Default.KeyboardArrowRight,
        contentDescription = "OK"
    )

    Image(
        modifier = Modifier
            .padding(top = 480.dp, end = 200.dp, start = 60.dp)
            .size(73.dp)
            .clickable { },
        painter = painterResource(id = R.drawable.notification),
        contentDescription = "Notifications icon"
    )

    Text(
        modifier = Modifier
            .padding(top = 500.dp, start = 170.dp)
            .clickable { },
        text = "Notifications",
        fontSize = 20.sp,
        fontFamily = firasans,
        fontWeight = FontWeight.Medium
    )
    Icon(
        modifier = Modifier
            .padding(top = 492.dp, start = 330.dp)
            .size(40.dp)
            .clickable { },
        imageVector = Icons.Default.KeyboardArrowRight,
        contentDescription = "OK"
    )

    Image(
        modifier = Modifier
            .padding(top = 590.dp, end = 200.dp, start = 60.dp)
            .size(73.dp)
            .clickable {
                val googleNotesPackage = "com.google.android.keep"
                val intent = context.packageManager.getLaunchIntentForPackage(googleNotesPackage)

                if (intent != null) {
                    // Notes app is installed
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else {
                    // Notes app not installed, open in browser
                    val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = android.net.Uri.parse("https://keep.google.com/")
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(browserIntent)
                }
            },
        painter = painterResource(id = R.drawable.notes),
        contentDescription = "Notes icon"
    )

    Text(
        modifier = Modifier
            .padding(top = 615.dp, start = 170.dp)
            .clickable {
                val googleNotesPackage = "com.google.android.keep"
                val intent = context.packageManager.getLaunchIntentForPackage(googleNotesPackage)

                if (intent != null) {
                    // Notes app is installed
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else {
                    // Notes app not installed, open in browser
                    val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = android.net.Uri.parse("https://keep.google.com/")
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(browserIntent)
                }
            },
        text = "Notes",
        fontSize = 20.sp,
        fontFamily = firasans,
        fontWeight = FontWeight.Medium
    )
    Icon(
        modifier = Modifier
            .padding(top = 610.dp, start = 330.dp)
            .size(40.dp)
            .clickable {
                val googleNotesPackage = "com.google.android.keep"
                val intent = context.packageManager.getLaunchIntentForPackage(googleNotesPackage)

                if (intent != null) {
                    // Notes app is installed
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else {
                    // Notes app not installed, open in browser
                    val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = android.net.Uri.parse("https://keep.google.com/")
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(browserIntent)
                }
            },
        imageVector = Icons.Default.KeyboardArrowRight,
        contentDescription = "OK"
    )

    Image(
        modifier = Modifier
            .padding(top = 697.dp, end = 200.dp, start = 60.dp)
            .size(73.dp)
            .clickable {
                val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(android.content.Intent.EXTRA_TEXT, "Check out this awesome Event!")
                }

                val chooserIntent = android.content.Intent.createChooser(
                    shareIntent,
                    "Share via"
                )

                ContextCompat.startActivity(context, chooserIntent, null)
            },
        painter = painterResource(id = R.drawable.share),
        contentDescription = "Share icon"
    )

    Text(
        modifier = Modifier
            .padding(top = 725.dp, start = 170.dp)
            .clickable {
                val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(android.content.Intent.EXTRA_TEXT, "Check out this awesome Event!")
                }

                val chooserIntent = android.content.Intent.createChooser(
                    shareIntent,
                    "Share via"
                )

                ContextCompat.startActivity(context, chooserIntent, null)
            },
        text = "Share",
        fontSize = 20.sp,
        fontFamily = firasans,
        fontWeight = FontWeight.Medium
    )
    Icon(
        modifier = Modifier
            .padding(top = 715.dp, start = 330.dp)
            .size(40.dp)
            .clickable {
                val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(android.content.Intent.EXTRA_TEXT, "Check out this awesome Event!")
                }

                val chooserIntent = android.content.Intent.createChooser(
                    shareIntent,
                    "Share via"
                )

                ContextCompat.startActivity(context, chooserIntent, null)
            },
        imageVector = Icons.Default.KeyboardArrowRight,
        contentDescription = "OK"
    )

}