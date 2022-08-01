package com.yusifmammadov.samplechatapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yusifmammadov.samplechatapp.presentation.chat.ChatScreen
import com.yusifmammadov.samplechatapp.presentation.home.HomeScreen
import com.yusifmammadov.samplechatapp.presentation.login.LoginScreen
import com.yusifmammadov.samplechatapp.presentation.registration.RegistrationScreen
import com.yusifmammadov.samplechatapp.presentation.splash.SplashScreen

@Composable
fun ChatNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier) {

        composable(Screen.Splash.route) {
            SplashScreen(navigateToRoute = {
                navController.popBackStack()
                navController.navigate(it)
            })
        }

        composable(Screen.Login.route) {
            LoginScreen(navigateToRoute = { route ->
                if (route == Screen.Home.route) {
                    navController.popBackStack()
                }
                navController.navigate(route)
            })
        }

        composable(Screen.Registration.route) {
            RegistrationScreen(navigateToRoute = {
                navController.popBackStack()
                navController.navigate(it)
            })
        }

        composable(Screen.Home.route) {
            HomeScreen(navigateToRoute = { route ->
                if (route == Screen.Login.route) {
                    navController.popBackStack()
                }
                navController.navigate(route)
            })
        }

        composable(Screen.Chat.route + "/{userId}") { backStackEntry ->
            ChatScreen(
                userId = backStackEntry.arguments?.getString("userId")!!,
                navigateToRoute = { route ->
                    if (route == Screen.Login.route) {
                        navController.popBackStack()
                        navController.popBackStack()
                    }
                    navController.navigate(route)
                }
            )
        }
    }
}