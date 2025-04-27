package com.io.eventer.ui.home.user

import android.annotation.SuppressLint
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.io.eventer.ui.theme.firasans
import com.io.eventer.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun User(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    modifier = Modifier.height(110.dp),
                    title = {
                        Text(
                            text = "User Screen",
                            fontSize = 32.sp,
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
                UserContent(navController)
            }
        }
    }
}

@Composable
fun UserContent(navController: NavController) {
    // State for user information with error tracking
    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var surname by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }

    // State for error messages
    var userNameError by remember { mutableStateOf<String?>(null) }
    var nameError by remember { mutableStateOf<String?>(null) }
    var surnameError by remember { mutableStateOf<String?>(null) }
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
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
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
            onValueChange = {
                userName = it
                userNameError = validateUsername(it.text)
            },
            errorMessage = userNameError,
            modifier = Modifier.padding(bottom = 16.dp),
            labelColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        UserInfoTextField(
            label = "Name",

            textValue = name,
            onValueChange = {
                name = it
                nameError = validateName(it.text)
            },
            errorMessage = nameError,
            labelColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        UserInfoTextField(
            label = "Surname",
            labelColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textValue = surname,
            onValueChange = {
                surname = it
                surnameError = validateSurname(it.text)
            },
            errorMessage = surnameError,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        UserInfoTextField(
            label = "Email",
            labelColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textValue = email,
            onValueChange = {
                email = it
                emailError = validateEmail(it.text)
            },
            errorMessage = emailError,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(55.dp),
            onClick = {
                // Validate everything
                userNameError = validateUsername(userName.text)
                nameError = validateName(name.text)
                surnameError = validateSurname(surname.text)
                emailError = validateEmail(email.text)

                // Check for errors
                val hasErrors = listOf(userNameError, nameError, surnameError, emailError)
                    .any { it != null }

                if (!hasErrors) {
                    saveUserInformation(
                        userName.text,
                        name.text,
                        surname.text,
                        email.text
                    )

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
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF82C8E5)
            )
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
    textValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
            modifier = Modifier.padding(bottom = 8.dp)
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

fun validateName(name: String): String? {
    return when {
        name.isBlank() -> "Name cannot be empty"
        name.length < 2 -> "Name must be at least 2 characters long"
        name.length > 50 -> "Name cannot exceed 50 characters"
        !name.matches(Regex("^[a-zA-Z]+$")) -> "Name can only contain letters"
        else -> null
    }
}

fun validateSurname(surname: String): String? {
    return when {
        surname.isBlank() -> "Surname cannot be empty"
        surname.length < 2 -> "Surname must be at least 2 characters long"
        surname.length > 50 -> "Surname cannot exceed 50 characters"
        !surname.matches(Regex("^[a-zA-Z]+$")) -> "Surname can only contain letters"
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
    name: String,
    surname: String,
    email: String
) {
    // TODO: Implement your saving mechanism
    println("Saving User Information:")
    println("Username: $userName")
    println("Name: $name")
    println("Surname: $surname")
    println("Email: $email")
}