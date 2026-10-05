package com.example.data.supabase

import com.example.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object SupabaseClientProvider {
    private val url: String = BuildConfig.SUPABASE_URL
    private val publishableKey: String = BuildConfig.SUPABASE_PUBLISHABLE_KEY

    init {
        require(url.isNotBlank() && publishableKey.isNotBlank()) {
            "Supabase is not configured. Add SUPABASE_URL and SUPABASE_PUBLISHABLE_KEY to local.properties."
        }
    }

    val client: SupabaseClient by lazy {
        createSupabaseClient(url, publishableKey) {
            install(Auth) {
                autoLoadFromStorage = true
                alwaysAutoRefresh = true
            }
            install(Postgrest)
            install(Storage)
            install(Realtime)
        }
    }
}
