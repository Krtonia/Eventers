package com.io.eventer.ui.auth.api

import com.io.eventer.model.User

data class AuthResponse(
    val token: String,
    val user: User
)