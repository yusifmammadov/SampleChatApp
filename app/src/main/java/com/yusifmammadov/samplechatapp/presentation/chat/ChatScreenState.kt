package com.yusifmammadov.samplechatapp.presentation.chat

import com.yusifmammadov.samplechatapp.data.model.MessageUi

data class ChatScreenState(
    val isLoading: Boolean = false,
    val messages: List<MessageUi> = emptyList(),
    val messageValue: String = "",
    val userName: String = "Chats"
)
