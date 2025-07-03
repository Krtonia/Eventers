package com.io.eventer.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String? = null,
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val summary: String = "",
    val imageUrl: String = "https://i.ibb.co/xK4KPDvn/Screenshot-2025-04-29-224917.png",
    val createdAt: String,
    val user_id: String? = null,
    val code: String = ""
)