package com.yusifmammadov.samplechatapp.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusifmammadov.samplechatapp.R
import com.yusifmammadov.samplechatapp.data.ChatRepository
import com.yusifmammadov.samplechatapp.navigation.Screen
import com.yusifmammadov.samplechatapp.util.Event
import com.yusifmammadov.samplechatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: ChatRepository):
    ViewModel() {

    var state by mutableStateOf(LoginScreenState())

    private val _eventChannel = Channel<Event>()
    val eventChannel = _eventChannel.receiveAsFlow()

    // TODO verify email address
    fun signIn(){
        if (state.emailValue.isNotEmpty() && state.passwordValue.isNotEmpty()) {
            viewModelScope.launch {
                repository.signIn(state.emailValue, state.passwordValue)
                    .collect { resource ->
                        when(resource) {
                            is Resource.Success -> {
                                withContext(Dispatchers.Main.immediate) {
                                    state = state.copy(isLoading = false)
                                    _eventChannel.send(Event.NavigateTo(Screen.Home.route))
                                }
                            }
                            is Resource.Loading -> state = state.copy(isLoading = true)
                            is Resource.Error -> {
                                withContext(Dispatchers.Main.immediate) {
                                    state = state.copy(isLoading = false)
                                    _eventChannel.send(Event.ShowToast(R.string.login_error))
                                }
                            }
                        }
                    }
            }
        } else { viewModelScope.launch {
            withContext(Dispatchers.Main.immediate) {
                _eventChannel.send(Event.ShowToast(R.string.empty_field_error))
            }
        }
        }
    }

    fun onEmailValueChanged(s: String) {
        state = state.copy(emailValue = s)
    }

    fun onPasswordValueChanged(s: String) {
        state = state.copy(passwordValue = s)
    }
}