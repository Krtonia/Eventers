package com.io.eventer.di

import android.content.Context
import android.util.Log
import com.io.eventer.util.Constants.KEY
import com.io.eventer.util.Constants.URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(@ApplicationContext context: Context): SupabaseClient {
        try {
            return createSupabaseClient(
                supabaseUrl = URL, //YOUR PROJECT URL
                supabaseKey = KEY //YOUR SUPABASE KEY
            ) {
                install(Auth)
                install(Postgrest)
                defaultSerializer = KotlinXSerializer()
            }
        } catch (e: Exception) {
            Log.e("SupabaseModule", "Error initializing Supabase client", e)
            throw RuntimeException("Failed to initialize Supabase client: ${e.message}", e)
        }
    }
}