
package com.borrowed.app.data.repository

import com.borrowed.app.data.remote.SupabaseClientProvider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

/**
 * Handles sign-in, sign-up and sign-out requests with Supabase.
 */
class AuthRepository(
    private val client: SupabaseClient = SupabaseClientProvider.client
) {

    val sessionStatus
        get() = client.auth.sessionStatus

    suspend fun signIn(emailAddress: String, password: String) {
        client.auth.signInWith(Email) {
            email = emailAddress
            this.password = password
        }
    }

    suspend fun signUp(emailAddress: String, password: String) {
        client.auth.signUpWith(Email) {
            email = emailAddress
            this.password = password
        }
    }

    suspend fun signOut() {
        client.auth.signOut()
    }
}
