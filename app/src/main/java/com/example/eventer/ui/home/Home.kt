package com.example.eventer.ui.home

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eventer.R
import com.example.eventer.navigation.Routes
import com.example.eventer.ui.theme.firasans
import android.util.Size
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController) {
    var cardCount by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val cardList = remember { mutableStateListOf<String>() }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        topBar = { TopAppBarContent() },
        floatingActionButton = {
            ExtendedFloatingActionButton(elevation = FloatingActionButtonDefaults.elevation(12.dp),
                containerColor = Color(0xFF82C8E5),
                onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
                Text(
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = 5.dp),
                    text = "New",
                    color = Color.Black,
                    fontFamily = firasans,
                    fontWeight = FontWeight.Medium
                )
            }
        },
    ) {
        Card(cardList = cardList)
        if (showDialog) {
            EventDialog(onDismiss = { showDialog = false }, onConfirm = { customText ->
                cardList.add(customText)
            })
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = (Color(0xFFF2F1F6))) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        NavigationBarItem(selected = currentRoute == Routes.third,
            colors = NavigationBarItemDefaults.colors(Color(0xFF0047AB)),
            onClick = { navController.navigate(Routes.third) },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text(text = "Home") })
        NavigationBarItem(selected = currentRoute == Routes.fourth,
            colors = NavigationBarItemDefaults.colors(Color(0xFF0047AB)),
            onClick = { navController.navigate(Routes.fourth) },
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile") },
            label = { Text(text = "Profile") })
        NavigationBarItem(selected = currentRoute == Routes.fifth,
            colors = NavigationBarItemDefaults.colors(Color(0xFF0047AB)),
            onClick = { navController.navigate(Routes.fifth) },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Options"
                )
            },
            label = { Text(text = "Options") })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent() {
    var showQRScanner by remember { mutableStateOf(false) }

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

    Image(
        modifier = Modifier
            .padding(start = 380.dp, top = 61.dp)
            .size(33.dp)
            .clickable { showQRScanner = true },
        painter = painterResource(id = R.drawable.qr),
        contentDescription = "QR scanner"
    )

    if (showQRScanner) {
        QRScanner(
            onQRCodeScanned = { qrContent ->
                // Handle the scanned QR code content here
                println("You're Scanned QR Code: $qrContent")
            },
            onDismiss = { showQRScanner = false }
        )
    }
}


@Composable
fun EventDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var eventText by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF82C8E5)
        ), onClick = {
            if (eventText.isNotBlank()) {
                onConfirm(eventText)
                onDismiss()
            }
        }) {
            Text(text = "Add", color = Color.Black)
        }
    }, title = {
        Text(
            text = "Create an Event",
            modifier = Modifier.padding(5.dp),
            fontFamily = firasans,
            fontWeight = FontWeight.SemiBold
        )
    }, text = {
        OutlinedTextField(
            value = eventText,
            onValueChange = { eventText = it },
            shape = RoundedCornerShape(25.dp),
            placeholder = { Text("Event Description") },
            modifier = Modifier.fillMaxWidth()
        )
    }, dismissButton = {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF82C8E5)
            ), onClick = onDismiss
        ) { Text(text = "Cancel", color = Color.Black) }
    })
}

@Composable
fun Card(cardList: List<String>) {
    Column(
        modifier = Modifier.padding(top = 76.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(9.dp))

        LazyColumn {
            items(cardList) { cardText ->
                ElevatedCard(
                    onClick = { /* TODO: Card action */ },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .height(250.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Column {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(id = R.drawable.party),
                            contentDescription = "Home page Image"
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                text = cardText,
                                fontSize = 22.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}


@Suppress("DEPRECATION")
@androidx.annotation.OptIn(ExperimentalGetImage::class)
@Composable
fun QRScanner(
    onQRCodeScanned: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    var permissionRequested by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!permissionRequested) {
            permissionRequested = true
            launcher.launch(android.Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(
                    modifier = Modifier.padding(14.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF82C8E5)
                    ), onClick = onDismiss
                ) {
                    Text(text = "Close", color = Color.Black)
                }
            },
            text = {
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .fillMaxWidth()
                ) {
                    AndroidView(
                        factory = { context ->
                            val previewView = PreviewView(context).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                            }

                            val preview = Preview.Builder().build()
                            preview.setSurfaceProvider(previewView.surfaceProvider)

                            val imageAnalysis = ImageAnalysis.Builder()
                                .setTargetResolution(Size(1280, 720))
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()

                            val scanner = BarcodeScanning.getClient()
                            val executor = Executors.newSingleThreadExecutor()

                            imageAnalysis.setAnalyzer(executor) { imageProxy ->
                                val mediaImage = imageProxy.image
                                if (mediaImage != null) {
                                    val image = InputImage.fromMediaImage(
                                        mediaImage,
                                        imageProxy.imageInfo.rotationDegrees
                                    )
                                    scanner.process(image)
                                        .addOnSuccessListener { barcodes ->
                                            for (barcode in barcodes) {
                                                barcode.rawValue?.let { qrContent ->
                                                    onQRCodeScanned(qrContent)
                                                    onDismiss()
                                                }
                                            }
                                        }
                                        .addOnCompleteListener {
                                            imageProxy.close()
                                        }
                                } else {
                                    imageProxy.close()
                                }
                            }
                            try {
                                val cameraProvider = cameraProviderFuture.get()
                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            previewView
                        }
                    )
                }
            }
        )
    }
}



















