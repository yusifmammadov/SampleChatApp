package com.yusifmammadov.samplechatapp.presentation.splash

import androidx.lifecycle.ViewModel
import com.yusifmammadov.samplechatapp.data.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: ChatRepository):
    ViewModel() {

        val isUserSignedIn: Boolean
        get() = repository.isUserSignedIn()

}