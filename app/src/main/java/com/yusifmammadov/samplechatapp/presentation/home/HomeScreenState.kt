package com.yusifmammadov.samplechatapp.presentation.home

import com.yusifmammadov.samplechatapp.data.model.User

data class HomeScreenState(
    val isLoading: Boolean = false,
    val listUsers: List<User> = emptyList()
)
