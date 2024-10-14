package com.example.todoapp.model

import com.example.todoapp.data.ClienteDtoUpload
import com.example.todoapp.data.EmpleadoDto
import com.example.todoapp.data.UsuarioDto
import com.example.todoapp.data.EmpleadoDtoUpload
import com.example.todoapp.data.UsuarioDtoUpload
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun getEmpleado(): List<EmpleadoDto>
    suspend fun signIn(userEmail: String, userPassword: String): Boolean
    suspend fun signUp(cliente: ClienteDtoUpload, usuario: UsuarioDtoUpload, userEmail: String, userPassword: String): Boolean
    suspend fun empleadoSignUp(empleado: EmpleadoDtoUpload, usuario: UsuarioDtoUpload, userEmail: String, userPassword: String): Boolean
    suspend fun signOut()
    suspend fun checkIfUserIdInTable(userId: Int): String?
    suspend fun checkUserId(username: String)
    suspend fun checkRole()
    suspend fun getUsuarioById(userId: Int): UsuarioDto?
    suspend fun updateUsuario(usuario: UsuarioDto)
    suspend fun updatePassword(username: String, password: String)
    suspend fun checkAdmin(userId: Int): Boolean
    val errorMessage: StateFlow<String>
    val sessionState: StateFlow<SessionStatus>
    val username: StateFlow<String>
    val role: StateFlow<String>
    val userId: StateFlow<Int>
}
