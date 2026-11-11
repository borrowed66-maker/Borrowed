package com.borrowed.app.data.remote

import com.borrowed.app.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth

/**
 * Provides one shared Supabase client for the app.
 *
 * The project URL and publishable key are read from local.properties
 * through BuildConfig. Do not put a service-role key in an Android app.
 */
object SupabaseClientProvider {

    val client by lazy {
        require(BuildConfig.SUPABASE_URL.isNotBlank()) {
            "Supabase URL is missing. Add SUPABASE_URL to local.properties."
        }
        require(BuildConfig.SUPABASE_PUBLISHABLE_KEY.isNotBlank()) {
            "Supabase publishable key is missing. Add SUPABASE_PUBLISHABLE_KEY to local.properties."
        }

        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_PUBLISHABLE_KEY
        ) {
            install(Auth)
        }
    }
}
