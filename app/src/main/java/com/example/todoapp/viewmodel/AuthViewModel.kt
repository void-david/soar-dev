package com.example.todoapp.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.ClienteDtoUpload
import com.example.todoapp.data.Empleado
import com.example.todoapp.data.EmpleadoDto
import com.example.todoapp.data.UsuarioDtoUpload
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
        phone: Long
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Create ClienteDtoUpload object
                val cliente = ClienteDtoUpload(
                    nombre = nombre,
                    apellido1 = apellido1,
                    apellido2 = apellido2,
                    ciudad = ciudad,
                    sector = sector,
                    calle = calle,
                    numero = numero
                )

                // Create UsuarioDto object
                val usuario = UsuarioDtoUpload(
                    username = username,
                    password = _password.value,
                    phone = phone
                )

                // Pass both cliente and usuario to the signUp function in the repository
                userRepository.signUp(
                    cliente = cliente,
                    usuario = usuario,
                    userEmail = _email.value,
                    userPassword = _password.value
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
}
