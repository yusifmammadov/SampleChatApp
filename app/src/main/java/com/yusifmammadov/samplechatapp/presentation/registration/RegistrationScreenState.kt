package com.yusifmammadov.samplechatapp.presentation.registration

data class RegistrationScreenState(
    val isLoading: Boolean = false,
    val emailValue: String = "",
    val passwordValue: String = "",
    val userNameValue: String = ""
)