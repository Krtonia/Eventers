package com.io.eventer.ui.home

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.io.eventer.navigation.Routes
import com.io.eventer.ui.theme.firasans
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.io.eventer.R
import com.io.eventer.model.Event
import com.io.eventer.ui.home.event.viewmodel.EventViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController, viewModel: EventViewModel = hiltViewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    var showImageUpdateDialog by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        topBar = { TopAppBarContent() },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                elevation = FloatingActionButtonDefaults.elevation(12.dp),
                onClick = { showDialog = true }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
                Text(
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = 5.dp),
                    text = "New",
                    fontFamily = firasans,
                    fontWeight = FontWeight.Medium
                )
            }
        },
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.error?.let { error ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text("Error: $error")
            }
        }
        EventCards(
            events = uiState.events,
            onImageClick = { eventId ->
                showImageUpdateDialog = eventId
            },
            onEventClick = { eventId ->
                navController.navigate("${Routes.tenth}/$eventId")
            },
            onDeleteEvent = viewModel::deleteEvent
        )
        if (showDialog) {
            EventDialog(
                onDismiss = { showDialog = false },
                onConfirm = { eventTitle ->
                    viewModel.createEvent(eventTitle)
                    showDialog = false
                }
            )
        }
        showImageUpdateDialog?.let { eventId ->
            ImageUpdateDialog(
                onDismiss = { showImageUpdateDialog = null },
                onConfirm = { imageUrl ->
                    viewModel.updateEventImage(eventId, imageUrl)
                    showImageUpdateDialog = null
                }
            )
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        NavigationBarItem(
            selected = currentRoute == Routes.fourth,
            onClick = { navController.navigate(Routes.fourth) },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text(text = "Home") })
        NavigationBarItem(
            selected = currentRoute == Routes.fifth,
            onClick = { navController.navigate(Routes.fifth) },
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile") },
            label = { Text(text = "Profile") })
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent() {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                text = "Welcome",
                fontSize = 42.sp,
                fontFamily = firasans,
                fontWeight = FontWeight.SemiBold
            )
        }
    )
}


@Composable
fun EventDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var eventText by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        Button(onClick = {
            if (eventText.isNotBlank()) {
                onConfirm(eventText)
                onDismiss()
            }
        }) {
            Text(text = "Add")
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
            onClick = onDismiss
        ) { Text(text = "Cancel") }
    })
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EventCards(
    events: List<Event>,
    onImageClick: (String) -> Unit,
    onEventClick: (String) -> Unit,
    viewModel: EventViewModel = hiltViewModel(),
    onDeleteEvent: (String) -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(9.dp))
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(1f),

            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                isRefreshing = true
                viewModel.refreshEvents()
                isRefreshing = false
            },
        ) {
            if (events.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No Events Yet",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = firasans
                        )
                        Text(
                            text = "Create your first event using the + New button",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            fontFamily = firasans
                        )
                    }
                }
            } else {
                LazyColumn {
                    items(events) { event ->
                        ElevatedCard(
                            onClick = {
                                event.id?.let { id ->
                                    if (id.isNotBlank()) {
                                        onEventClick(id)
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .padding(vertical = 10.dp, horizontal = 20.dp)
                                .height(250.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            shape = RoundedCornerShape(22.dp)
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clickable {
                                            event.id?.let { id ->
                                                if (id.isNotBlank()) {
                                                    onImageClick(id)
                                                }
                                            }
                                        }
                                ) {
                                    GlideImage(
                                        model = event.imageUrl,
                                        contentDescription = "Event Image (Tap to change)",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    ) { requestBuilder ->
                                        requestBuilder
                                            .placeholder(R.drawable.placeholder)
                                            .error(R.drawable.placeholder)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(8.dp)
                                            .background(
                                                color = Color.Black.copy(alpha = 0.6f),
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                    ) {
                                        Text(
                                            text = "Tap to change image",
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                    ) {
                                        IconButton(onClick = { onDeleteEvent(event.id ?: "") }) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Delete"
                                            )
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 12.dp)
                                ) {
                                    Text(
                                        text = event.title,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    if (event.description.isNotEmpty()) {
                                        Text(
                                            text = event.description,
                                            fontSize = 14.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            color = Color.Gray,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.End)
                                    ) {
                                        Text(
                                            text = "Tap to view details",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageUpdateDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var imageUrl by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (imageUrl.isNotBlank()) {
                        onConfirm(imageUrl)
                    }
                }
            ) {
                Text(text = "Update")
            }
        },
        title = {
            Text(
                text = "Update Event Image",
                fontFamily = firasans,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                shape = RoundedCornerShape(25.dp),
                placeholder = { Text("Enter image URL") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}