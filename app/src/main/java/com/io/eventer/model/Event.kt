package com.io.eventer.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String? = null,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "https://images.unsplash.com/photo-1511285605577-4d62fb50d2f7?q=80&w=2076&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    val createdAt: String = "",
    val user_id: String? = null
)