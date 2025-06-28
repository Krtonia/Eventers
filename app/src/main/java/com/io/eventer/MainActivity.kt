package com.io.eventer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.io.eventer.navigation.Navigation
import com.io.eventer.ui.theme.EventerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventerTheme(dynamicColor = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    handleDeepLink(intent)
                    Navigation()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent) {
        val data = intent.data
        Log.d("DeepLink", "Received intent data: $data")

        if (data != null) {
            if (data.scheme == "eventer" && data.host == "reset-password") {
                val fragment = data.fragment

                if (!fragment.isNullOrEmpty()) {
                    val params = parseFragment(fragment)
                    val error = params["error"]

                    if (error != null) {
                        val errorDescription = params["error_description"]
                        val sharedPref = getSharedPreferences("deep_link_params", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("reset_error", errorDescription ?: "Link has expired")
                            putBoolean("should_show_error", true)
                            apply()
                        }
                        return
                    }

                    val accessToken = params["access_token"]
                    val refreshToken = params["refresh_token"]

                    if (accessToken != null) {
                        val email = extractEmailFromJWT(accessToken)

                        val sharedPref = getSharedPreferences("deep_link_params", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("reset_token", accessToken)
                            putString("reset_email", email)
                            putString("refresh_token", refreshToken ?: "")
                            putBoolean("should_navigate_to_reset", true)
                            apply()
                        }
                    }
                }
            }
        }
    }

    private fun extractEmailFromJWT(token: String): String {
        return try {
            val parts = token.split(".")
            if (parts.size >= 2) {
                val payload = String(android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE))
                val json = org.json.JSONObject(payload)
                json.getString("email")
            } else {
                ""
            }
        } catch (e: Exception) {
            Log.e("DeepLink", "Error extracting email from JWT", e)
            ""
        }
    }

    private fun parseFragment(fragment: String): Map<String, String> {
        val params = mutableMapOf<String, String>()
        fragment.split("&").forEach { param ->
            val keyValue = param.split("=", limit = 2)
            if (keyValue.size == 2) {
                params[keyValue[0]] = java.net.URLDecoder.decode(keyValue[1], "UTF-8")
            }
        }
        return params
    }
}