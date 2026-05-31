package com.example.mobileprogrammingarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun observeIsLoggedIn(): Flow<Boolean>
    fun currentUserEmail(): String?
    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
    fun logout()
}
