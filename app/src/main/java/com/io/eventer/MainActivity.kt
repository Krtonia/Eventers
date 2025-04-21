package com.io.eventer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.io.eventer.navigation.Navigation
import com.io.eventer.ui.theme.EventerTheme
import com.example.eventer.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventerTheme {
              Navigation()
            }
        }
    }
}
