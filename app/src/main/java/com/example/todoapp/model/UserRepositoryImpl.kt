package com.example.todoapp.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.todoapp.data.EmpleadoDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val postgrest: Postgrest,
    private val auth: Auth,
) : UserRepository {

    private val _sessionState = MutableStateFlow<SessionStatus>(SessionStatus.LoadingFromStorage)
    override val sessionState: StateFlow<SessionStatus> get() = _sessionState

    private val _errorMessage = MutableStateFlow<String>("")
    override val errorMessage: StateFlow<String> get() = _errorMessage

    init {
        CoroutineScope(Dispatchers.IO).launch {
            // Listener para cambios de sesión
            supabaseClient.auth.sessionStatus.collect { sessionStatus ->
                Log.d("UserRepository", "Session status changed: $sessionStatus")
                _sessionState.value = sessionStatus
            }
        }
    }

    override suspend fun getEmpleado(): List<EmpleadoDto> {
            return try {
                withContext(Dispatchers.IO){
                    Log.d("UserRepository", "Fetching Empleado...")

                    val result = postgrest.from("Empleado")
                        .select()
                        .decodeList<EmpleadoDto>()
                    Log.d("UserRepository", "Fetched Empleado: $result")
                    result
                }
            } catch (e: Exception) {
                Log.e("UserRepository", "Error fetching Empleado: ${e.localizedMessage}", e)
                emptyList()
            }
        }

    val isLoading = mutableStateOf(false)

    override suspend fun signIn(userEmail: String, userPassword: String): Boolean {
        isLoading.value = true
        _errorMessage.value = ""
        return try {
            auth.signInWith(Email) {
                this.email = userEmail
                this.password = userPassword
            }
            if (sessionState.value is SessionStatus.Authenticated) {
                Log.d("UserRepositoryImpl", "Sign-in successful for: $userEmail")
                return true
            } else {
                Log.e("UserRepositoryImpl", "Sign-in failed, session not authenticated")
                Log.d("UserRepositoryImpl", "Session status: ${sessionState.value}")
                return false
            }
        } catch (e: Exception) {
            _errorMessage.value = e.message ?: "Unknown error"
            false
        } finally {
            isLoading.value = false
        }
    }


    override suspend fun signUp(userEmail: String, userPassword: String): Boolean {
        return try {
            auth.signUpWith(Email) {
                email = userEmail
                password = userPassword
            }
            true
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Sign-up failed: ${e.message}")
            false
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}
