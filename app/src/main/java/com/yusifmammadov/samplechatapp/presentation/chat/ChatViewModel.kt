package com.yusifmammadov.samplechatapp.presentation.chat

import androidx.lifecycle.ViewModel
import com.yusifmammadov.samplechatapp.data.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
): ViewModel() {


}