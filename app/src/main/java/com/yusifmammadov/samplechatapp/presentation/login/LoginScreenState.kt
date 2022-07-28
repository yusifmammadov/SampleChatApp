package com.yusifmammadov.samplechatapp.presentation.login

data class LoginScreenState (
    val isLoading: Boolean = false,
    val emailValue: String = "",
    val passwordValue: String = ""
)