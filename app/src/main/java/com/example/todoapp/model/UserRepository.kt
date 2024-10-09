package com.example.todoapp.model

import com.example.todoapp.data.EmpleadoDto
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun getEmpleado(): List<EmpleadoDto>
    suspend fun signIn(userEmail: String, userPassword: String): Boolean
    suspend fun signUp(userEmail: String, userPassword: String): Boolean
    suspend fun signOut()
    suspend fun checkIfUserIdInTable(userId: Int): String?
    suspend fun checkUserId(username: String)
    suspend fun checkRole()
    val errorMessage: StateFlow<String>
    val sessionState: StateFlow<SessionStatus>
    val username: StateFlow<String>
    val role: StateFlow<String>
    val userId: StateFlow<Int>
}
