package com.io.eventer.model

data class EventState(
    val isAuthenticated: Boolean = false,
    val userId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)