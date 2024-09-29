package com.example.todoapp.model

import com.example.todoapp.data.EmpleadoDto
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun getEmpleado(): List<EmpleadoDto>
    suspend fun signIn(userEmail: String, userPassword: String): Boolean
    suspend fun signUp(userEmail: String, userPassword: String): Boolean
    suspend fun signOut()
    val sessionState: StateFlow<SessionStatus>
}
