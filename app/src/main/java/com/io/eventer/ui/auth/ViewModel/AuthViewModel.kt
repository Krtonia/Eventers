package com.io.eventer.ui.auth.ViewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.io.eventer.ui.auth.api.AuthRequest
import com.io.eventer.ui.auth.api.AuthState
import com.io.eventer.ui.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val appContext: Application
) : AndroidViewModel(appContext) {

    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    fun login(email: String, password: String) = viewModelScope.launch {
        authState = AuthState.Loading
        try {
            val response = repository.login(AuthRequest(email = email, password = password))
            if (response.isSuccessful) {
                response.body()?.let {
                    saveToken(it.token)
                    authState = AuthState.Success(it.user)
                } ?: run { authState = AuthState.Error("Invalid response") }
            } else {
                authState = AuthState.Error("Login failed")
            }
        } catch (e: Exception) {
            authState = AuthState.Error(e.localizedMessage ?: "Error hua")
        }
    }

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        authState = AuthState.Loading
        try {
            val response = repository.register(AuthRequest(name, email, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    saveToken(it.token)
                    authState = AuthState.Success(it.user)
                } ?: run { authState = AuthState.Error("Invalid response") }
            } else {
                authState = AuthState.Error("Signup failed")
            }
        } catch (e: Exception) {
            authState = AuthState.Error(e.localizedMessage ?: "Error hua")
        }
    }

    private fun saveToken(token: String) {
        val masterKey = MasterKey.Builder(appContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val prefs = EncryptedSharedPreferences.create(
            appContext,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        prefs.edit().putString("auth_token", token).apply()
    }
}
