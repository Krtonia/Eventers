package com.io.eventer.ui.auth.components

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    object EmailUpdateSent : AuthState()
    data class Error(val message: String) : AuthState()
}