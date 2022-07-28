package com.yusifmammadov.samplechatapp.util

import androidx.annotation.StringRes

sealed class Event {

    class NavigateTo(val route: String): Event()
    class ShowToast(@StringRes val message: Int): Event()
}