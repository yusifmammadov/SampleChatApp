package com.yusifmammadov.samplechatapp.presentation.chat

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import com.yusifmammadov.samplechatapp.presentation.home.ChatAppBar
import com.yusifmammadov.samplechatapp.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    userId: String,
    navigateToRoute: (String) -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ChatAppBar(title = state.userName) {
                viewModel.signOut()
            }
        },
        content = { paddingValues ->
            ChatContent(
                messages = state.messages,
                messageValue = state.messageValue,
                onMessageValueChanged = {
                    viewModel.onMessageValueChanged(it)
                },
                onSendMessage = {
                    viewModel.sendMessage()
                })
        }
    )

    LaunchedEffect(viewModel, lifecycle) {
        withContext(Dispatchers.Main.immediate) {
            viewModel.eventChannel.collect { event ->
                when(event) {
                    is Event.ShowToast -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                    is Event.NavigateTo -> {
                        navigateToRoute(event.route)
                    }
                }
            }
        }
    }
}