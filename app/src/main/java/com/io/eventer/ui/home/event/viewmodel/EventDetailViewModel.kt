package com.io.eventer.ui.home.event.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.eventer.model.Event
import com.io.eventer.model.EventDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient
) : ViewModel() {

    private val _eventState = MutableStateFlow(EventDetailState())
    val eventState: StateFlow<EventDetailState> = _eventState.asStateFlow()

    fun fetchEvent(eventId: String) {
        viewModelScope.launch {
            _eventState.update { it.copy(isLoading = true) }
            try {
                if (!isValidUUID(eventId)) {
                    _eventState.update {
                        it.copy(
                            isLoading = false,
                            error = "Invalid event ID format"
                        )
                    }
                    return@launch
                }

                val event = supabaseClient.postgrest["events"]
                    .select {
                        filter {
                            eq("id", eventId)
                        }
                    }
                    .decodeSingle<Event>()
                _eventState.update { it.copy(event = event, isLoading = false, error = null) }
                checkIfCreator(eventId)
            } catch (e: Exception) {
                Log.e("EventDetailViewModel", "Error fetching event: ${e.message}", e)
                _eventState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun checkIfCreator(eventId: String) {
        viewModelScope.launch {
            try {
                val currentUser = supabaseClient.auth.currentUserOrNull()
                val userId = currentUser?.id
                if (userId != null) {
                    val event = _eventState.value.event ?: supabaseClient.postgrest["events"]
                        .select {
                            filter {
                                eq("id", eventId)
                            }
                        }
                        .decodeSingle<Event>()
                    val isCreator = event.user_id == userId
                    _eventState.update { it.copy(isCreator = isCreator) }
                } else {
                    _eventState.update { it.copy(isCreator = false) }
                }
            } catch (e: Exception) {
                Log.e("EventDetailViewModel", "Error checking creator status", e)
                _eventState.update { it.copy(isCreator = false) }
            }
        }
    }

    fun updateEvent(eventId: String, title: String, description: String, summary: String) {
        viewModelScope.launch {
            _eventState.update { it.copy(isLoading = true) }
            try {
                val currentUser = supabaseClient.auth.currentUserOrNull()
                val userId = currentUser?.id
                if (userId == null) {
                    _eventState.update {
                        it.copy(
                            isLoading = false,
                            error = "User not authenticated"
                        )
                    }
                    return@launch
                }
                val event = _eventState.value.event
                if (event?.user_id != userId) {
                    _eventState.update {
                        it.copy(
                            isLoading = false,
                            error = "Only the creator can update this event"
                        )
                    }
                    return@launch
                }
                val updatedData = mapOf(
                    "title" to title,
                    "description" to description,
                    "summary" to summary
                )
                supabaseClient.postgrest["events"]
                    .update(updatedData) {
                        filter {
                            eq("id", eventId)
                        }
                    }
                fetchEvent(eventId)
                _eventState.update { it.copy(isLoading = false, error = null) }
            } catch (e: Exception) {
                Log.e("EventDetailViewModel", "Error updating event", e)
                _eventState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun isValidUUID(uuidString: String): Boolean {
        return try {
            UUID.fromString(uuidString)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}