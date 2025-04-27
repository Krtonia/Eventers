package com.io.eventer.ui.auth.components

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import javax.inject.Inject

class AuthRepository @Inject constructor(private val supabase: SupabaseClient) {

    fun signup(name: String, emailValue: String, passwordValue: String): Flow<AuthState> = flow {
        emit(AuthState.Loading)
        try {

            val userData = JsonObject(mapOf("name" to JsonPrimitive(name)))

            val response = supabase.auth.signUpWith(Email) {
                email = emailValue
                password = passwordValue
                data = userData
            }
            emit(AuthState.Success)
        } catch (e: Exception) {
            emit(AuthState.Error(e.localizedMessage ?: "Registration failed"))
        }
    }

    fun login(emailValue: String, passwordValue: String): Flow<AuthState> = flow {
        emit(AuthState.Loading)
        try {
            supabase.auth.signInWith(Email) {
                email = emailValue
                password = passwordValue
            }
            supabase.auth.refreshCurrentSession()
            emit(AuthState.Success)
        }
        catch (e: Exception) {
            emit(AuthState.Error(e.localizedMessage ?: "Login failed"))
        }
    }

    //Function to get CurrentUser details
    fun getCurrentUser(): UserInfo? {
        return try {
            supabase.auth.currentUserOrNull()
        } catch (e: Exception) {
            null
        }
    }
}