package com

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventer.ui.Hello
import com.example.eventer.ui.Home
import com.example.eventer.ui.Options
import com.example.eventer.ui.Profile
import com.example.eventer.ui.Welcome

@Composable
fun navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.first) {
        composable(Routes.first) { Welcome(navController) }
        composable(Routes.second) { Hello(navController) }
        composable(Routes.third){Home(navController)}
        composable(Routes.fourth) { Profile(navController) }
        composable(Routes.fifth){ Options(navController) }
    }
}