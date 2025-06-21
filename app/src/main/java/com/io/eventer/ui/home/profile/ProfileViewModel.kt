package com.io.eventer.ui.home.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.eventer.ui.auth.components.AuthRepository
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

    init {
        fetchUserProfile()
    }

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> get() = _profileImageUri

    private fun fetchUserProfile() {
        val currentUser = authRepository.getCurrentUser()
        if (currentUser != null) {
            _username.value = currentUser.userMetadata?.get("name")?.toString()?.removeSurrounding("\"")
                ?: currentUser.email.toString()
        }
    }

    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            try {
                authRepository.updateUsername(newUsername)
                _username.value = newUsername
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setProfileImage(uri: Uri?) {
        _profileImageUri.value = uri
    }
}
