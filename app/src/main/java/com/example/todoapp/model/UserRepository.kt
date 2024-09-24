package com.example.todoapp.model

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserRepository(scope: CoroutineScope) {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://cfgapznwhntzgwwbpprk.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNmZ2Fwem53aG50emd3d2JwcHJrIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcyNjc4Mjk4NCwiZXhwIjoyMDQyMzU4OTg0fQ.yOBDZhZi-dVLpz_-Q1fFRte5tW1r3q0I6pXDTv3gy2k"
    ) {
        install(Auth)
    }

    private val _sessionState = MutableStateFlow<SessionStatus>(SessionStatus.LoadingFromStorage)
    val sessionState: StateFlow<SessionStatus> get() = _sessionState

    init {
        scope.launch {
            // Listener para cambios de sesión
            supabase.auth.sessionStatus.collect { sessionStatus ->
                _sessionState.value = sessionStatus
            }
        }
    }


    suspend fun signIn(userEmail: String, userPassword: String) {
        try {
            // Log the input email and password (for debugging purposes, remove in production)
            Log.d("UserRepository", "Attempting sign-in with email: $userEmail")

            // Perform the sign-in request
            val session = supabase.auth.signInWith(Email) {
                email = userEmail
                password = userPassword
            }

            // Log the response size to check for large data
            Log.d("UserRepository", "Sign-in successful. Response size: ${session.toString().length} characters")

        } catch (e: Exception) {
            // Log any exceptions to help debug
            Log.e("UserRepository", "Sign-in failed: ${e.message}")
            throw e // Rethrow the exception so the ViewModel can handle it
        }
    }


    suspend fun signUp(userEmail: String, userPassword: String) {
        supabase.auth.signUpWith(Email) {
            email = userEmail
            password = userPassword
        }
    }

    suspend fun signOut() {
        supabase.auth.signOut()
    }

}