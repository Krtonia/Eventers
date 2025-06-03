package com.io.eventer.ui.home.event.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.io.eventer.model.Event
import com.io.eventer.model.EventState
import com.io.eventer.model.EventUiState
import com.io.eventer.navigation.Routes
import com.io.eventer.ui.auth.components.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class EventViewModel @Inject constructor(private val supabaseClient: SupabaseClient) : ViewModel() {

    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    private val _authState = MutableStateFlow(EventState())
    val authState: StateFlow<EventState> = _authState.asStateFlow()

    init {
        fetchEvents()
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            try {
                val session = supabaseClient.auth.currentSessionOrNull()
                if (session != null) {
                    _authState.update {
                        it.copy(
                            isAuthenticated = true,
                            userId = session.user?.id,
                            error = null
                        )
                    }
                    fetchEvents()
                } else {
                    _authState.update {
                        it.copy(
                            isAuthenticated = false,
                            userId = null,
                            error = "Not authenticated"
                        )
                    }
                }
            } catch (e: Exception) {
                _authState.update {
                    it.copy(
                        isAuthenticated = false,
                        userId = null,
                        error = e.message
                    )
                }
                Log.e("EventViewModel", "Auth check failed", e)
            }
        }
    }

    fun fetchEvents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val events = supabaseClient.postgrest["events"].select().decodeList<Event>()
                _uiState.update { it.copy(events = events, isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun createEvent(title: String, description: String = "", imageUrl: String = "") {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val userId = authState.value.userId
                if (userId == null) {
                    _uiState.update { it.copy(isLoading = false, error = "User not authenticated") }
                    return@launch
                }

                val dateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val formattedDate = dateFormat.format(Date())

                val eventCode = generateSixDigitCode()

                val newEvent = Event(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    description = description,
                    imageUrl = imageUrl,
                    createdAt = formattedDate,
                    user_id = userId,
                    code = generateSixDigitCode()
                )

                Log.d("EventViewModel", "Creating event: $newEvent")
                supabaseClient.postgrest["events"].insert(newEvent)
                Log.d("EventViewModel", "Event created successfully")
                fetchEvents() // Refresh
            } catch (e: Exception) {
                Log.e("EventViewModel", "Error creating event", e)
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun signOut(navController: NavController) {
        viewModelScope.launch {
            try {
                supabaseClient.auth.signOut()
                _authState.update {
                    EventState(isAuthenticated = false, userId = null, error = null)
                }
                _uiState.update { EventUiState() }
                navController.navigate(Routes.second) {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            } catch (e: Exception) {
                _authState.update { it.copy(error = e.message) }
                Log.e("EventViewModel", "Sign out failed", e)
            }
        }
    }

    fun updateEventImage(eventId: String, imageUrl: String) {
        viewModelScope.launch {
            try {
                val userId = authState.value.userId
                if (userId == null) {
                    _uiState.update { it.copy(error = "User not authenticated") }
                    return@launch
                }
                val updatedData = mapOf("imageUrl" to imageUrl)
                Log.d("EventViewModel", "Updating event image for ID: $eventId")

                supabaseClient.postgrest["events"]
                    .update(updatedData) {
                        filter {
                            eq("id", eventId)
                        }
                    }
                fetchEvents()
            } catch (e: Exception) {
                Log.e("EventViewModel", "Error updating event image", e)
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    private fun generateSixDigitCode(): String {
        val random = Random
        val code = StringBuilder()
        repeat(6) {
            code.append(random.nextInt(10))
        }
        return code.toString()
    }

    fun createShareableText(event: Event): String {
        return "🎉 Join us for ${event.title}!\n\nUse code: ${event.code}\n\nDon't miss out on this amazing event!"
    }

}