package com.io.eventer.ui.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.io.eventer.R
import com.io.eventer.navigation.Routes
import com.io.eventer.ui.auth.viewmodel.AuthViewModel
import com.io.eventer.ui.auth.components.AuthState
import com.io.eventer.ui.theme.firasans
import io.github.jan.supabase.auth.admin.AdminUserBuilder

@Composable
fun ResetPassword(
    navController: NavController,
    email: String,
    accessToken: String,
    refreshToken: String
) {
    val viewModel: AuthViewModel = hiltViewModel()
    val state = viewModel.authState
    val context = LocalContext.current
    val loginMessage = viewModel.loginMessage

    var isTokenVerified by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Log.d("ResetPassword", "Email: $email")
        Log.d("ResetPassword", "Access Token: ${accessToken.take(20)}...")
        Log.d("ResetPassword", "Refresh Token: ${refreshToken.take(20)}...")

        // Set session using the tokens from deep link(Email session doesn't work mpw in supabase auth)
        viewModel.setSessionFromTokens(accessToken, refreshToken)
    }

    LaunchedEffect(state) {
        when (state) {
            is AuthState.Success -> {
                isTokenVerified = true
                viewModel.resetToIdle()
            }
            is AuthState.PasswordResetSuccess -> {
                Toast.makeText(context, "Password reset successful! Please sign in with your new password.", Toast.LENGTH_LONG).show()
                navController.navigate(Routes.second) {
                    popUpTo(0) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    LaunchedEffect(loginMessage) {
        loginMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.resetMessages()
        }
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lock))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 1f,
        
    )


    Box(modifier = Modifier.fillMaxSize()) {


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
        } else if (!isTokenVerified) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Invalid or Expired Link",
                    fontFamily = firasans,
                    color = if (!isSystemInDarkTheme()) Color.Black else Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "The password reset link is invalid or has expired. Please request a new one.",
                    fontFamily = firasans,
                    color = if (!isSystemInDarkTheme()) Color.Black else Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { navController.navigate(Routes.eleventh) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5F19F2)
                    )
                ) {
                    Text(
                        text = "Request New Link",
                        fontFamily = firasans,
                        color = if (!isSystemInDarkTheme()) Color.Black else Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .size(350.dp)
                        .padding(top = 0.dp)
                )

                Text(
                    text = "Reset Password",
                    fontFamily = firasans,
                    color = if (!isSystemInDarkTheme()) Color.Black else Color.White,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Enter your new password",
                    fontFamily = firasans,
                    color = if (!isSystemInDarkTheme()) Color.Black else Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(28.dp))

                var newPassword by remember { mutableStateOf("") }
                var confirmPassword by remember { mutableStateOf("") }
                var passwordVisible by remember { mutableStateOf(false) }
                var confirmPasswordVisible by remember { mutableStateOf(false) }

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = newPassword,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { newPassword = it },
                    placeholder = {
                        Text(
                            text = "New Password",
                            color = Color(0xFFD9D9D9)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.lock),
                            contentDescription = "password",
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = painterResource(R.drawable.eye),
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = Color.White
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF313E55),
                        unfocusedContainerColor = Color(0xFF313E55),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontFamily = firasans,
                        fontSize = 16.sp
                    ),
                    supportingText = {
                        if (newPassword.isNotBlank() && newPassword.length < 6) {
                            Text(
                                text = "Password must be at least 6 characters",
                                color = if (!isSystemInDarkTheme()) Color.Black else Color.White
                            )
                        }
                    },
                    isError = newPassword.isNotBlank() && newPassword.length < 6
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = confirmPassword,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { confirmPassword = it },
                    placeholder = {
                        Text(
                            text = "Confirm Password",
                            color = Color(0xFFD9D9D9)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.lock),
                            contentDescription = "confirm password",
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                painter = painterResource(R.drawable.eye),
                                contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                                tint = Color.White
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF313E55),
                        unfocusedContainerColor = Color(0xFF313E55),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontFamily = firasans,
                        fontSize = 16.sp
                    ),
                    supportingText = {
                        if (confirmPassword.isNotBlank() && newPassword != confirmPassword) {
                            Text(
                                text = "Passwords do not match",
                                color = Color.White
                            )
                        }
                    },
                    isError = confirmPassword.isNotBlank() && newPassword != confirmPassword
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (newPassword.length >= 6 && newPassword == confirmPassword) {
                            viewModel.resetPassword(newPassword)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5F19F2)
                    ),
                    enabled = newPassword.length >= 6 && newPassword == confirmPassword
                ) {
                    Text(
                        text = "Reset Password",
                        fontFamily = firasans,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
