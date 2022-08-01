package com.yusifmammadov.samplechatapp.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusifmammadov.samplechatapp.R
import com.yusifmammadov.samplechatapp.data.ChatRepository
import com.yusifmammadov.samplechatapp.data.model.Message
import com.yusifmammadov.samplechatapp.navigation.Screen
import com.yusifmammadov.samplechatapp.util.Constants
import com.yusifmammadov.samplechatapp.util.Event
import com.yusifmammadov.samplechatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(ChatScreenState())

    private val receiverId = savedStateHandle.get<String>(Constants.USER_ID)

    private val _eventChannel = Channel<Event>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        getReceiverUsername()
        getMessages()
    }

    private fun getMessages() = viewModelScope.launch {
        repository.getMessages(receiverId!!).collect{ resource ->
            when(resource) {
                is Resource.Success -> {
                    state = state.copy(isLoading = false, messages = resource.data!!)
                }
                is Resource.Error -> {
                    state = state.copy(isLoading = false)
                    withContext(Dispatchers.Main.immediate) {
                        _eventChannel.send(Event.ShowToast(R.string.load_messages_error))
                    }
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }
    }


    fun sendMessage() {
        if (state.messageValue.isNotBlank()) {
            repository.sendMessage(receiverId!!,state.messageValue)
            state = state.copy(messageValue = "")
        }

    }

    private fun getReceiverUsername() {
        viewModelScope.launch {
            repository.getUsernameForId(receiverId!!).collect { userName ->
                state = state.copy(userName = userName)
            }
        }
    }

    fun onMessageValueChanged(s: String) {
        state = state.copy(messageValue = s)
    }

    fun signOut() = viewModelScope.launch {
        repository.signOut().collect { resource ->

            when(resource) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main.immediate) {
                        state = state.copy(isLoading = false)
                        _eventChannel.send(Event.NavigateTo(Screen.Login.route))
                    }
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
                is Resource.Error -> {
                    state = state.copy(isLoading = false)
                    _eventChannel.send(Event.ShowToast(R.string.log_out_error))
                }
            }
        }
    }


}