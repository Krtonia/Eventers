package com.io.eventer.ui.auth.api

data class AuthRequest(
    val name: String? = null,
    val email: String,
    val password: String
)