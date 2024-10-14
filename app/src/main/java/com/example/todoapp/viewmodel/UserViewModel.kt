package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Empleado
import com.example.todoapp.data.EmpleadoDto
import com.example.todoapp.model.UserRepository
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // Estado de sesión observable por la UI
    val sessionState: StateFlow<SessionStatus> = userRepository.sessionState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SessionStatus.LoadingFromStorage
    )
    var isSignedIn: Boolean = false

    // Estados adicionales para controlar la UI durante el proceso de autenticación
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    val errorMessage: StateFlow<String> = userRepository.errorMessage

    val role: StateFlow<String> = userRepository.role

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


    fun signOut() {
        viewModelScope.launch {
            userRepository.signOut()
        }
    }

    // StateFlow to hold the list of empleados
    private val _empleados = MutableStateFlow<List<Empleado>>(listOf())
    val empleados: StateFlow<List<Empleado>> get() = _empleados

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

    // Function to fetch empleados
    fun getEmpleado() {
        viewModelScope.launch {
            try {
                // Fetch the list of EmpleadoDto from the repository
                val result = userRepository.getEmpleado()
                // Map the result to the Empleado domain model
                _empleados.emit(result.map { it -> it.asDomainModel() })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Mapping function from EmpleadoDto to Empleado
    private fun EmpleadoDto.asDomainModel(): Empleado {
        return Empleado(
            empleadoId = this.empleadoId,
            jefeId = this.jefeId,
            matricula = this.matricula,
            admin = this.admin,
            usuarioId = this.usuarioId
        )
    }
}
