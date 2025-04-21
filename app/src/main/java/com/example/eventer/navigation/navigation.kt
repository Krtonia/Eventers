package com.example.eventer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventer.ui.auth.SignIn
import com.example.eventer.ui.home.options.Help
import com.example.eventer.ui.home.Home
import com.example.eventer.ui.home.info.Info
import com.example.eventer.ui.home.options.Options
import com.example.eventer.ui.home.profile.Profile
import com.example.eventer.ui.home.user.User
import com.example.eventer.ui.Welcome

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.first) {
        composable(Routes.first) { Welcome(navController) }
        composable(Routes.second) { SignIn(navController) }
        composable(Routes.third){ Home(navController) }
        composable(Routes.fourth) { Profile(navController) }
        composable(Routes.fifth){ Options(navController) }
        composable(Routes.sixth){ Info(navController) }
        composable(Routes.seventh){ Help(navController) }
        composable(Routes.eigth){ User(navController) }
    }
}