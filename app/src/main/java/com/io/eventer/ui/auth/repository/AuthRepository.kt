package com.io.eventer.ui.auth.repository

import com.io.eventer.ui.auth.api.AuthApiService
import com.io.eventer.ui.auth.api.AuthRequest

import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApiService
) {
    suspend fun login(request: AuthRequest) = api.login(request)
    suspend fun register(request: AuthRequest) = api.register(request)
}
