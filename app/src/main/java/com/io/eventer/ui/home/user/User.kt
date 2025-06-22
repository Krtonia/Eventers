package com.io.eventer.ui.home.user

import android.annotation.SuppressLint
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.io.eventer.ui.theme.firasans
import com.io.eventer.ui.home.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun User(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(modifier = Modifier.padding(horizontal = 5.dp, vertical = 8.dp),
                            text = "User Screen",
                            fontSize = 42.sp,
                            fontFamily = firasans,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                UserContent(viewModel = hiltViewModel())
            }
        }
    }
}

@Composable
fun UserContent(viewModel: ProfileViewModel) {

    // Local state for editing (separate from ViewModel)
    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }

    // State for error messages
    var userNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(top = 4.dp, bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Update your personal information",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        UserInfoTextField(
            label = "User Name",
            textValue = userName,
            onValueChange = { newValue ->
                userName = newValue
                userNameError = validateUsername(newValue)
            },
            errorMessage = userNameError,
            modifier = Modifier.padding(bottom = 16.dp),
            labelColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        )

        UserInfoTextField(
            label = "Email",
            labelColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textValue = userEmail, // Use local state
            onValueChange = { newValue ->
                userEmail = newValue // Update local state only
                emailError = validateEmail(newValue)
            },
            errorMessage = emailError,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .height(55.dp).align(Alignment.CenterHorizontally),
            onClick = {
                // Validate everything
                userNameError = validateUsername(userName)
                emailError = validateEmail(userEmail)

                // Check for errors
                val hasErrors = listOf(userNameError, emailError)
                    .any { it != null }

                if (!hasErrors) {
                    // Only update Save is clicked
                    viewModel.updateUsername(userName)
                    viewModel.updateEmail(userEmail)

                    Toast.makeText(
                        context,
                        "User information saved successfully!",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        context,
                        "Please correct the errors in the form",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
        ) {
            Text(
                text = "Save",
                fontFamily = firasans,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun UserInfoTextField(
    label: String,
    labelColor: Color,
    textValue: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "$label:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = firasans,
            color = labelColor,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = textValue,
            shape = RoundedCornerShape(25.dp),
            textStyle = TextStyle(color = Color.White),
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = label,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (errorMessage != null) Color.Red else Color.White.copy(alpha = 0.5f),
                focusedBorderColor = if (errorMessage != null) Color.Red else Color.White
            ),
            isError = errorMessage != null
        )

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
        }
    }
}

fun validateUsername(username: String): String? {
    return when {
        username.isBlank() -> "Username cannot be empty"
        username.length < 3 -> "Username must be at least 3 characters long"
        username.length > 50 -> "Username cannot exceed 50 characters"
        !username.matches(Regex("^[a-zA-Z0-9_]+$")) -> "Username can only contain letters, numbers, and underscores"
        else -> null
    }
}

fun validateEmail(email: String): String? {
    return when {
        email.isBlank() -> "Email cannot be empty"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
        email.length > 100 -> "Email cannot exceed 100 characters"
        else -> null
    }
}

fun saveUserInformation(
    userName: String,
    email: String
) {
    // TODO: Implement your saving mechanism
    println("Saving User Information:")
    println("Username: $userName")
    println("Email: $email")
}