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
import com.io.eventer.ui.auth.components.AuthState
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
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 8.dp),
                            text = "User Screen",
                            style = MaterialTheme.typography.headlineLarge,
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

/*@Composable
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
            textValue = userEmail,
            onValueChange = { newValue ->
                userEmail = newValue
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
}*/

@Composable
fun UserContent(viewModel: ProfileViewModel) {

    val initialUserName by viewModel.username.collectAsState()
    val emailUpdateState by viewModel.emailUpdateState.collectAsState()

    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }

    var userNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    LaunchedEffect(emailUpdateState) {
        when (emailUpdateState) {
            is AuthState.EmailUpdateSent -> {
                Toast.makeText(
                    context,
                    "Confirmation email sent! Check your inbox to verify new email.",
                    Toast.LENGTH_LONG
                ).show()
                userEmail = ""
            }

            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    "Error: ${(emailUpdateState as AuthState.Error).message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Current credentials
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.15f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Current Credentials:",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Username: $initialUserName",
                    fontWeight = FontWeight.Black,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Email: ${viewModel.getCurrentEmail()}",
                    fontWeight = FontWeight.Black,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // warning card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.15f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "⚠️ Regarding Email Update:",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Changing email requires verification. You'll receive a confirmation email. Use your OLD email to login until you confirm the new one. Clicking the change email button in mail will change email ",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Enter new information",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        UserInfoTextField(
            label = "New User Name",
            textValue = userName,
            onValueChange = { newValue: String ->
                userName = newValue
                userNameError = validateUsername(newValue)
            },
            errorMessage = userNameError,
            modifier = Modifier.padding(bottom = 16.dp),
            labelColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        )

        UserInfoTextField(
            label = "New Email",
            labelColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textValue = userEmail,
            onValueChange = { newValue: String ->
                userEmail = newValue
                emailError = validateEmail(newValue)
            },
            errorMessage = emailError,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .height(55.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                // Validate (can't still verify if the email entered exists or not)
                userNameError = if (userName.isNotEmpty()) validateUsername(userName) else null
                emailError = if (userEmail.isNotEmpty()) validateEmail(userEmail) else null

                val hasErrors = listOf(userNameError, emailError).any { it != null }

                if (!hasErrors) {
                    // Update username
                    if (userName.isNotEmpty()) {
                        viewModel.updateUsername(userName)
                        userName = ""
                        Toast.makeText(
                            context,
                            "Username updated successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // Update email
                    if (userEmail.isNotEmpty()) {
                        viewModel.updateEmail(userEmail)
                    }

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
                text = "Update",
                fontFamily = firasans,
                fontWeight = FontWeight.SemiBold,
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
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (errorMessage != null) Color.Red else Color.Unspecified,
                focusedBorderColor = if (errorMessage != null) Color.Red else Color.Unspecified
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