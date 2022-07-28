package com.yusifmammadov.samplechatapp.presentation.chat

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    userId: String,
    viewModel: ChatViewModel = hiltViewModel()
) {

    Text(text = userId)
}