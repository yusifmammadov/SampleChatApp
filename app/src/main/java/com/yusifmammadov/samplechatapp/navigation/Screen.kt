package com.yusifmammadov.samplechatapp.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash_screen")
    object Login: Screen("login_screen")
    object Registration: Screen("registration_screen")
    object Home: Screen("home_screen")
    object Chat: Screen("chat_screen")
}