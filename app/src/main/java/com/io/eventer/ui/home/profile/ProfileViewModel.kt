package com.io.eventer.ui.home.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.eventer.ui.auth.components.AuthRepository
import com.io.eventer.ui.auth.components.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _useremail = MutableStateFlow("")
    val useremail: StateFlow<String> get() = _useremail

    private val _emailUpdateState = MutableStateFlow<AuthState>(AuthState.Idle)
    val emailUpdateState: StateFlow<AuthState> get() = _emailUpdateState

    init {
        fetchUserProfile()
    }

    private val _profileImageUri = MutableStateFlow<Uri?>(null)

    private fun fetchUserProfile() {
        val currentUser = authRepository.getCurrentUser()
        if (currentUser != null) {
            _username.value = currentUser.userMetadata?.get("name")?.toString()?.removeSurrounding("\"")
                ?: currentUser.email.toString()
            _useremail.value = currentUser.email ?: ""
        }
    }

    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            authRepository.updateUsername(newUsername).collect { state ->
                when (state) {
                    is AuthState.Loading -> _isLoading.value = true
                    is AuthState.Success -> {
                        _isLoading.value = false
                        _username.value = newUsername
                    }
                    is AuthState.Error -> {
                        _isLoading.value = false
                        Log.e("ProfileViewModel","failed to update Username")
                    }
                    else -> {Log.d("ProfileViewModel","Successfully updated Username")}
                }
            }
        }
    }

    fun updateEmail(newEmail: String) {
        viewModelScope.launch {
            authRepository.updateEmail(newEmail).collect { state ->
                _emailUpdateState.value = state
            }
        }
    }

    fun getCurrentEmail(): String {
        return authRepository.getCurrentUser()?.email ?: ""
    }

    /*fun updateEmail(newEmail: String) {
        viewModelScope.launch {
            authRepository.updateEmail(newEmail).collect { state ->
                when (state) {
                    is AuthState.Loading -> _isLoading.value = true
                    is AuthState.Success -> {
                        _isLoading.value = false
                        _useremail.value = newEmail
                    }
                    is AuthState.Error -> {
                        _isLoading.value = false
                        Log.e("ProfileViewModel","failed to update Email")
                    }
                    else -> {Log.d("ProfileViewModel","Successfully updated Email")}
                }
            }
        }
    }*/

    fun setProfileImage(uri: Uri?) {
        _profileImageUri.value = uri
    }
}
