package com.io.eventer.ui.home.event

import com.io.eventer.model.Event

data class EventUiState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String? = null
)
