package com.example.eventer.ui.home.event

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class EventViewModel:ViewModel() {
    private val jh = MutableStateFlow(EventUiState())
}