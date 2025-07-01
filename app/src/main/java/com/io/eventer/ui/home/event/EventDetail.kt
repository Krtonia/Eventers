package com.io.eventer.ui.home.event

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.io.eventer.R
import com.io.eventer.model.Event
import com.io.eventer.ui.home.event.viewmodel.EventDetailViewModel
import com.io.eventer.ui.theme.firasans

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun EventDetail(
    navController: NavController,
    eventId: String?,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val eventState = viewModel.eventState.collectAsState()
    val event = eventState.value.event
    val isEditing = remember { mutableStateOf(false) }
    val isCreator = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(eventId) {
        if (eventId.isNullOrBlank()) {
            Log.e("EventDetail", "Event ID is null or empty")
            return@LaunchedEffect
        }
        viewModel.fetchEvent(eventId)
        viewModel.checkIfCreator(eventId)
    }

    LaunchedEffect(eventState.value.isCreator) {
        isCreator.value = eventState.value.isCreator
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Event Details",
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = firasans,
                    fontWeight = FontWeight.SemiBold
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            if (isCreator.value) {
                Row {
                    FloatingActionButton(
                        onClick = { isEditing.value = !isEditing.value },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Event"
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            event?.let {
                                val eventCode = it.code.ifEmpty { "No code available" }
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_SUBJECT, "Join us for this event: ${it.title}")
                                    putExtra(Intent.EXTRA_TEXT, """
                                        Join us for: ${it.title}
                                        
                                        ${if (it.description.isNotEmpty()) "Description: ${it.description}" else ""}
                                        ${if (it.summary.isNotEmpty()) "Summary: ${it.summary}" else ""}
                                        
                                        Use code: $eventCode to join!
                                    """.trimIndent())
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Share Event"))
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Event"
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        if (eventState.value.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (eventState.value.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Error loading event: ${eventState.value.error}",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )

                    Button(
                        onClick = {
                            eventId?.let { viewModel.fetchEvent(it) }
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Try Again")
                    }
                }
            }
        } else if (event != null) {
            if (isEditing.value) {
                EventEditForm(
                    event = event,
                    onSave = { title, description, summary ->
                        eventId?.let {
                            viewModel.updateEvent(it, title, description, summary)
                        }
                        isEditing.value = false
                    },
                    onCancel = { isEditing.value = false },
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        GlideImage(
                            model = event.imageUrl,
                            contentDescription = "Event Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        ) { requestBuilder ->
                            requestBuilder
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        if (event.code.isNotEmpty()) {
                            Card(
                                modifier = Modifier.padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.15f)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Event Code",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Medium
                                    )

                                    Text(
                                        text = event.code,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 2.sp,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )

                                    Text(
                                        text = "Share this code with your guests",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Description",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Text(
                            text = if (event.description.isNotEmpty()) event.description else "No description provided",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        if (event.summary.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Summary",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            Text(
                                text = event.summary,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Event ID: ${event.id}",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No event data available")
            }
        }
    }
}

@Composable
fun EventEditForm(
    event: Event,
    onSave: (String, String, String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(event.title) }
    var description by remember { mutableStateOf(event.description) }
    var summary by remember { mutableStateOf(event.summary ?: "") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Edit Event",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
            value = summary,
            onValueChange = { summary = it },
            label = { Text("Summary") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(8.dp)
        )

        if (event.code.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Event Code (cannot be edited)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = event.code,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = { onSave(title, description, summary) }
            ) {
                Text("Save Changes")
            }
        }
    }
}