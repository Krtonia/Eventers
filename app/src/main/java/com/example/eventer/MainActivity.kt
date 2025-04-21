package com.example.eventer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.eventer.navigation.Navigation
import com.example.eventer.navigation.Routes
import com.example.eventer.ui.theme.EventerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventerTheme {
                //navController.navigate(Routes.third)
              Navigation()
            }
        }
    }
}
