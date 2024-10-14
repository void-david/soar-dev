package com.example.todoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.ClienteDtoUpload
import com.example.todoapp.data.UsuarioDto
import com.example.todoapp.data.Empleado
import com.example.todoapp.data.EmpleadoDto
import com.example.todoapp.data.EmpleadoDtoUpload
import com.example.todoapp.data.UsuarioDtoUpload
import com.example.todoapp.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // Estado de sesión observable por la UI
    val sessionState: StateFlow<SessionStatus> = userRepository.sessionState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SessionStatus.LoadingFromStorage
    )
    var isSignedIn: Boolean = false
    var updatedUser by mutableStateOf(false)

    // Estados adicionales para controlar la UI durante el proceso de autenticación
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    val errorMessage: StateFlow<String> = userRepository.errorMessage

    val role: StateFlow<String> = userRepository.role

    val username: StateFlow<String> = userRepository.username

    val userId: StateFlow<Int> = userRepository.userId

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun signIn() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                userRepository.signIn(
                    userEmail = _email.value,
                    userPassword = _password.value
                )
            } catch (e: Exception) {
                Log.e("UserViewModel", "Sign-in failed: ${errorMessage.value}")
            } finally {
                _isLoading.value = false
                isSignedIn = true
            }
        }
    }
    fun isAuthenticated(): Boolean {
        return isSignedIn
    }

    fun signUp(
        nombre: String,      // Add necessary fields to gather data for ClienteDtoUpload
        apellido1: String,
        apellido2: String,
        ciudad: String,
        sector: String,
        calle: String,
        numero: String,
        username: String,     // Fields for UsuarioDto
        password: String,
        phone: Long
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Create ClienteDtoUpload object
                val cliente = ClienteDtoUpload(
                    ciudad = ciudad,
                    sector = sector,
                    calle = calle,
                    numero = numero
                )

                // Create UsuarioDto object
                val usuario = UsuarioDtoUpload(
                    username = username,
                    password = password,
                    phone = phone,
                    name = nombre,
                    lastName1 = apellido1,
                    lastName2 = apellido2,
                    role = "Cliente"
                )

                // Pass both cliente and usuario to the signUp function in the repository
                userRepository.signUp(
                    cliente = cliente,
                    usuario = usuario,
                    userEmail = username,
                    userPassword = password
                )
            } catch (e: Exception) {
                Log.e("UserViewModel", "Sign-up failed: ${errorMessage.value}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun empleadoSignUp(
        nombre: String,      // Add necessary fields to gather data for ClienteDtoUpload
        apellido1: String,
        apellido2: String,
        matricula: String,
        admin: Boolean,
        jefeId: Int,
        username: String,     // Fields for UsuarioDto
        password: String,
        phone: Long
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Create ClienteDtoUpload object
                val empleado = EmpleadoDtoUpload(
                    matricula = matricula,
                    admin = admin,
                    jefeId = jefeId,
                    usuarioId = null
                )

                // Create UsuarioDto object
                val usuario = UsuarioDtoUpload(
                    username = username,
                    password = password,
                    phone = phone,
                    name = nombre,
                    lastName1 = apellido1,
                    lastName2 = apellido2,
                    role = "Cliente"
                )

                // Pass both cliente and usuario to the signUp function in the repository
                userRepository.empleadoSignUp(
                    empleado = empleado,
                    usuario = usuario,
                    userEmail = username,
                    userPassword = password
                )
            } catch (e: Exception) {
                Log.e("UserViewModel", "Sign-up failed: ${errorMessage.value}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            userRepository.signOut()
        }
    }

    init {
        Log.d("UserViewModel", role.value)
        if (role.value != "Empleado" && role.value != "Cliente") {
            checkRole()
        }
    }

    fun checkRole(){
        viewModelScope.launch {
            userRepository.checkRole()
        }
    }

    fun checkIfUserIdInTable(userId: Int) {
        viewModelScope.launch {
            userRepository.checkIfUserIdInTable(userId)
        }
    }

    // AuthViewModel.kt
    suspend fun getUsuarioById(userId: Int): UsuarioDto? = suspendCancellableCoroutine { continuation ->
        viewModelScope.launch {
            val result = userRepository.getUsuarioById(userId)
            continuation.resume(result) { exception ->
                Log.e("AuthViewModel", "Error fetching Usuario: ${exception.localizedMessage}", exception)
            }
        }
    }

    suspend fun updateUsuario(usuario: UsuarioDto) {
        try{
            viewModelScope.launch{
                userRepository.updateUsuario(usuario)
                updatedUser = true
            }
        } catch (e: Exception){
            Log.e("UpdateUserAuthVM", "Error updating Usuario: ${e.localizedMessage}", e)
        }
    }

    suspend fun checkAdmin(userId: Int): Boolean {
        return userRepository.checkAdmin(userId)
    }
}
