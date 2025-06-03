package com.io.eventer.model

data class EventDetailState(
    val isLoading: Boolean = false,
    val event: Event? = null,
    val error: String? = null,
    val isCreator: Boolean = false
)