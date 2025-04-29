package com.io.eventer.ui.auth.components

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

data class State(
    val isAuthenticated: Boolean = false,
    val userId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)