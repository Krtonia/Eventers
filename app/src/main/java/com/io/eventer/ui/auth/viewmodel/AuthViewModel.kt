package com.io.eventer.ui.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.eventer.ui.auth.components.AuthRepository
import com.io.eventer.ui.auth.components.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val client: SupabaseClient
) : ViewModel() {

    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    var registrationMessage by mutableStateOf<String?>(null)
        private set

    var loginMessage by mutableStateOf<String?>(null)
        private set

    var passwordResetMessage by mutableStateOf<String?>(null)
        private set

    fun register(name: String, email: String, password: String) {
        registrationMessage = null
        authState = AuthState.Loading

        authRepository.signup(name, email, password)
            .onEach { state ->
                authState = state
                if (state is AuthState.Success) {
                    registrationMessage = "Registration successful! Please log in."
                } else if (state is AuthState.Error) {
                    registrationMessage = state.message
                }
            }.launchIn(viewModelScope)
    }

    fun login(email: String, password: String) {
        loginMessage = null
        authState = AuthState.Loading

        authRepository.login(email, password)
            .onEach { state ->
                authState = state
                if (state is AuthState.Success) {
                    loginMessage = "Login successful!"
                } else if (state is AuthState.Error) {
                    loginMessage = state.message
                }
            }.launchIn(viewModelScope)
    }

    fun sendPasswordResetEmail(email: String) {
        passwordResetMessage = null
        authState = AuthState.Loading

        authRepository.sendPasswordResetEmail(email)
            .onEach { state ->
                authState = state
                when (state) {
                    is AuthState.PasswordResetEmailSent -> {
                        passwordResetMessage = "Password reset email sent successfully!"
                    }
                    is AuthState.Error -> {
                        passwordResetMessage = state.message
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun resetMessages() {
        registrationMessage = null
        loginMessage = null
        passwordResetMessage = null
    }

    fun isUserLoggedIn(): Boolean {
        try {
            val user = client.auth.currentUserOrNull()
            android.util.Log.d("AuthViewModel", "Current user: ${user?.email}")
            return user != null
        } catch (e: Exception) {
            android.util.Log.e("AuthViewModel", "Error checking user login status", e)
            return false
        }
    }
}