package com.io.eventer.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.io.eventer.ui.auth.SignIn
import com.io.eventer.ui.home.options.Help
import com.io.eventer.ui.home.Home
import com.io.eventer.ui.home.info.Info
import com.io.eventer.ui.home.options.Options
import com.io.eventer.ui.home.profile.Profile
import com.io.eventer.ui.home.user.User
import com.io.eventer.ui.Welcome
import com.io.eventer.ui.auth.PasswordReset
import com.io.eventer.ui.auth.ResetPassword
import com.io.eventer.ui.auth.SignUp
import com.io.eventer.ui.home.event.EventDetail


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val sharedPref = context.getSharedPreferences("deep_link_params", Context.MODE_PRIVATE)
        val shouldShowError = sharedPref.getBoolean("should_show_error", false)
        if (shouldShowError) {
            val errorMessage = sharedPref.getString("reset_error", "Link has expired")
            with(sharedPref.edit()) {
                remove("reset_error")
                remove("should_show_error")
                apply()
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            navController.navigate(Routes.eleventh) {
                popUpTo(Routes.first) { inclusive = false }
            }
            return@LaunchedEffect
        }

        val shouldNavigate = sharedPref.getBoolean("should_navigate_to_reset", false)
        if (shouldNavigate) {
            val accessToken = sharedPref.getString("access_token", "")
            val refreshToken = sharedPref.getString("refresh_token", "")
            val email = sharedPref.getString("reset_email", "")

            if (!accessToken.isNullOrEmpty()) {
                with(sharedPref.edit()) {
                    remove("access_token")
                    remove("refresh_token")
                    remove("reset_email")
                    remove("should_navigate_to_reset")
                    apply()
                }

                // Navigate with encoded parameters
                val encodedEmail = if (!email.isNullOrEmpty()) {
                    java.net.URLEncoder.encode(email, "UTF-8")
                } else {
                    "unknown"
                }
                val encodedAccessToken = java.net.URLEncoder.encode(accessToken, "UTF-8")
                val encodedRefreshToken = java.net.URLEncoder.encode(refreshToken ?: "", "UTF-8")

                navController.navigate("reset_password_confirm/$encodedEmail/$encodedAccessToken/$encodedRefreshToken") {
                    popUpTo(Routes.first) { inclusive = false }
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = Routes.first) {
        composable(Routes.first) { Welcome(navController) }
        composable(Routes.second) { SignIn(navController) }
        composable(Routes.third) { SignUp(navController) }
        composable(Routes.fourth) { Home(navController) }
        composable(Routes.fifth) { Profile(navController) }
        composable(Routes.sixth) { Options(navController) }
        composable(Routes.seventh) { Info(navController) }
        composable(Routes.eigth) { Help(navController) }
        composable(Routes.nineth) { User(navController) }
        composable(
            route = "${Routes.tenth}/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            EventDetail(navController, eventId)
        }
        composable(Routes.eleventh) { PasswordReset(navController) }
        composable(
            route = Routes.twelveth,
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = "unknown"
                },
                navArgument("accessToken") {
                    type = NavType.StringType
                },
                navArgument("refreshToken") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: "unknown"
            val accessToken = backStackEntry.arguments?.getString("accessToken") ?: ""
            val refreshToken = backStackEntry.arguments?.getString("refreshToken") ?: ""
            ResetPassword(navController, email, accessToken, refreshToken)
        }
    }
}