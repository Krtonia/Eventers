package com.io.eventer.ui.home.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.io.eventer.navigation.Routes
import com.io.eventer.ui.theme.firasans
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.io.eventer.R
import com.io.eventer.ui.theme.EventerTheme
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Profile(navController: NavController) {
    Scaffold(
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
                            imageVector = Icons.Default.List,
                            contentDescription = "Options"
                        )
                    },
                    label = { Text(text = "Options") }
                )
            }
        },
    ) { innerPadding ->
        ProfileContent(navController, Modifier.padding(innerPadding),viewModel = hiltViewModel())
    }
}

@Composable
fun ProfileContent(navController: NavController, modifier: Modifier = Modifier,viewModel: ProfileViewModel) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ProfileHeader(
            onImageSelected = { uri ->
                selectedImageUri = uri
            }
        )
        WelcomeText(viewModel = viewModel)
        ProfileMenuItems(navController)
    }
}

@Composable
fun ProfileHeader(onImageSelected: (Uri) -> Unit) {
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

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

    rememberLauncherForActivityResult(
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = if (imageUri != null) {
                    rememberAsyncImagePainter(imageUri)
                } else {
                    painterResource(id = R.drawable.test)
                },
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable { galleryLauncher.launch("image/*") }
            )

            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-12).dp, y = (-12).dp)
                    .size(40.dp)
                    .clickable { galleryLauncher.launch("image/*") },
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "Change Image"
            )
        }
    }
}

@Composable
fun WelcomeText(viewModel: ProfileViewModel) {
    val username by viewModel.username.collectAsState()
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Hello $username! What Brings You here?",
            fontSize = 26.sp,
            fontFamily = firasans,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProfileMenuItems(navController: NavController) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        ElevatedCard(modifier = Modifier.padding(horizontal = 20.dp)) {
            ProfileMenuItem(
                iconResId = R.drawable.profile,
                title = "My Profile",
                onClick = { navController.navigate(Routes.nineth) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(modifier = Modifier.padding(horizontal = 20.dp)) {

            ProfileMenuItem(
                iconResId = R.drawable.notification,
                title = "Notifications",
                onClick = { /* Handle notifications click */ }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(modifier = Modifier.padding(horizontal = 20.dp)) {

            ProfileMenuItem(
                iconResId = R.drawable.notes,
                title = "Notes",
                onClick = {
                    val googleNotesPackage = "com.google.android.keep"
                    val intent =
                        context.packageManager.getLaunchIntentForPackage(googleNotesPackage)

                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    } else {
                        val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = "https://keep.google.com/".toUri()
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(browserIntent)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard(modifier = Modifier.padding(horizontal = 20.dp)) {
            ProfileMenuItem(
                iconResId = R.drawable.share,
                title = "Share",
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Check out this awesome Event!")
                    }
                    val chooserIntent = Intent.createChooser(
                        shareIntent,
                        "Share via"
                    )
                    ContextCompat.startActivity(context, chooserIntent, null)
                }
            )
        }
    }
}

@Composable
fun ProfileMenuItem(
    iconResId: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "$title icon",
            colorFilter = ColorFilter.tint(color = if (isSystemInDarkTheme()) Color.White else Color.Black)
        )

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = title,
            fontSize = 18.sp,
            fontFamily = firasans,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Navigate",
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePreview() {
    EventerTheme(dynamicColor = true) {
        Profile(navController = rememberNavController())
    }
}