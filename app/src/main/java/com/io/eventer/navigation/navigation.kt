package com.io.eventer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.io.eventer.ui.auth.SignIn
import com.io.eventer.ui.home.options.Help
import com.io.eventer.ui.home.Home
import com.io.eventer.ui.home.info.Info
import com.io.eventer.ui.home.options.Options
import com.io.eventer.ui.home.profile.Profile
import com.io.eventer.ui.home.user.User
import com.io.eventer.ui.Welcome
import com.io.eventer.ui.auth.SignUp

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.first) {
        composable(Routes.first) { Welcome(navController) }
        composable(Routes.second) { SignIn(navController) }
        composable(Routes.third) { SignUp(navController) }
        composable(Routes.fourth){ Home(navController) }
        composable(Routes.fifth) { Profile(navController) }
        composable(Routes.sixth){ Options(navController) }
        composable(Routes.seventh){ Info(navController) }
        composable(Routes.eigth){ Help(navController) }
        composable(Routes.nineth){ User(navController) }
    }
}