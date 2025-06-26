package com.io.eventer.ui.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.io.eventer.R
import com.io.eventer.ui.auth.viewmodel.AuthViewModel
import com.io.eventer.ui.auth.components.AuthState
import com.io.eventer.ui.theme.firasans

@Composable
fun PasswordReset(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val state = viewModel.authState
    val context = LocalContext.current
    val passwordResetMessage = viewModel.passwordResetMessage

    LaunchedEffect(passwordResetMessage) {
        passwordResetMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.resetMessages()
        }
    }

    LaunchedEffect(state) {
        if (state is AuthState.PasswordResetEmailSent) {
            kotlinx.coroutines.delay(100)
            viewModel.resetToIdle()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (state is AuthState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Reset Password",
                    fontFamily = firasans,
                    color = Color.White,
                    fontSize = 45.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Enter your email address and we'll send you a link to reset your password",
                    fontFamily = firasans,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                var email by remember { mutableStateOf("") }
                var isTouched by remember { mutableStateOf(false) }
                val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = {
                        email = it
                        isTouched = true
                    },
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
                    isError = isTouched && !isEmailValid && email.isNotBlank(),
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
                        if (isTouched && !isEmailValid && email.isNotBlank()) {
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

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (isEmailValid && email.isNotBlank()) {
                            viewModel.sendPasswordResetEmail(email)
                        } else {
                            isTouched = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5F19F2),
                    )
                ) {
                    Text(
                        text = "Send Reset Link",
                        fontFamily = firasans,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 30.dp)) {
                        Text(
                            text = "Remember your password?",
                            fontFamily = firasans,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        TextButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Text(
                                text = "Back to Sign In",
                                fontFamily = firasans,
                                color = Color(0xFFAB90FA),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}