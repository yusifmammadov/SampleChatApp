package com.yusifmammadov.samplechatapp.util

sealed class Resource<T> (val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?): Resource<T>(data)
    class Loading<T>(isLoading: Boolean = true): Resource<T>()
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}