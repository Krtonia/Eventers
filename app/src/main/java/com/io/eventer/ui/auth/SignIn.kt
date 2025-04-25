package com.io.eventer.ui.auth

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.io.eventer.R
import com.io.eventer.navigation.Routes
import com.io.eventer.ui.auth.ViewModel.AuthViewModel
import com.io.eventer.ui.auth.api.AuthState
import com.io.eventer.ui.theme.firasans

@Composable
fun SignIn(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val state = viewModel.authState
    Column(
        modifier = Modifier
            .fillMaxSize(),

        ) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier,
            text = "Hello !",
            fontFamily = firasans,
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Right
        )

        Text(
            modifier = Modifier,
            text = "Welcome back to Eventers",
            fontFamily = firasans,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Right
        )

        Spacer(modifier = Modifier.height(60.dp))

        var email by remember { mutableStateOf("") }
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        TextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            value = email,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { email = it },
            placeholder = {
                Text(
                    text = "Email address",
                    lineHeight = 15.sp,
                    color = Color(0xFFD9D9D9)
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.mail),
                    contentDescription = "email",
                    tint = Color.White
                )
            },
            isError = !isEmailValid && email.isNotBlank(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF313E55),
                unfocusedContainerColor = Color(0xFF313E55),
                disabledContainerColor = Color(0xFF313E55),
                errorContainerColor = Color(0xFF313E55),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                errorCursorColor = Color.Red,
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
                errorLabelColor = Color.Transparent
            ),
            supportingText = {
                if (!isEmailValid && email.isNotBlank()) {
                    Text(
                        text = "Please enter a valid email address",
                        color = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = firasans,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
        )

        Spacer(modifier = Modifier.height(14.dp))

        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        val isPasswordInvalid = password.isNotBlank() && password.length < 6
        TextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            value = password,
            shape = RoundedCornerShape(12.dp),
            onValueChange = { password = it },
            placeholder = {
                Text(
                    text = "Password",
                    lineHeight = 15.sp,
                    color = Color(0xFFD9D9D9)
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.lock),
                    contentDescription = "password lock icon",
                    tint = Color.White
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(R.drawable.eye), tint = Color.White,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = isPasswordInvalid,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF313E55),
                unfocusedContainerColor = Color(0xFF313E55),
                disabledContainerColor = Color(0xFF313E55),
                errorContainerColor = Color(0xFF313E55),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                errorCursorColor = Color.Red,
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
                errorLabelColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            supportingText = {
                if (isPasswordInvalid) {
                    Text(
                        text = "Password must be at least 6 characters",
                        color = Color.White
                    )
                }
            },
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = firasans,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = { navController.navigate(Routes.fourth) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5F19F2),
            )
        ) {
            Text(
                text = "Log In",
                fontFamily = firasans, color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New to Eventers ?",
                fontFamily = firasans,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            TextButton(
                onClick = { navController.navigate(Routes.third) },
            ) {
                Text(
                    text = "Sign Up",
                    fontFamily = firasans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFAB90FA)
                )
            }
        }
    }

    when (state) {
        is AuthState.Loading -> CircularProgressIndicator()
        is AuthState.Success -> {
            LaunchedEffect(Unit) {
                navController.navigate(Routes.fourth) {
                    popUpTo(Routes.second) { inclusive = true }
                }
            }
        }

        is AuthState.Error -> {
            Text("Error: ${state.message}", color = Color.Red)
        }

        else -> {}
    }

}

