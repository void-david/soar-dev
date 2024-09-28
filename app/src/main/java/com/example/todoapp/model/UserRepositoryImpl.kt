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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val postgrest: Postgrest,
    private val auth: Auth,
    scope: CoroutineScope
) : UserRepository {

    private val _sessionState = MutableStateFlow<SessionStatus>(SessionStatus.LoadingFromStorage)
    override val sessionState: StateFlow<SessionStatus> get() = _sessionState

    init {
        scope.launch {
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
    val errorMessage = mutableStateOf("")

    override suspend fun signIn(userEmail: String, userPassword: String): Boolean {
        isLoading.value = true
        errorMessage.value = ""
        return try {
            auth.signInWith(Email) {
                this.email = userEmail
                this.password = userPassword
            }
            if (sessionState.value is SessionStatus.Authenticated) {
                Log.d("UserRepository", "Sign-in successful for: $userEmail")
                return true
            } else {
                Log.e("UserRepository", "Sign-in failed, session not authenticated")
                return false
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Sign-in failed: ${e.message}")
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
            Log.e("UserRepository", "Sign-up failed: ${e.message}")
            false
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}
