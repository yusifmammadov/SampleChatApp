package com.yusifmammadov.samplechatapp.presentation.home

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.yusifmammadov.samplechatapp.R
import com.yusifmammadov.samplechatapp.navigation.Screen
import com.yusifmammadov.samplechatapp.presentation.components.ProgressBar
import com.yusifmammadov.samplechatapp.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    if (state.isLoading) {
        ProgressBar()
    }
    
    Scaffold(
        topBar = {
            ChatAppBar(stringResource(id = R.string.chats)) {
                viewModel.signOut()
            }
        },
        content = {
            HomeContent(
                listUsers = state.listUsers) {
                navigateToRoute(Screen.Chat.route + "/$it")
            }
        }
    )
    
    
    LaunchedEffect(viewModel, lifecycle) {
        withContext(Dispatchers.Main.immediate) {
            viewModel.eventChannel.collect { event ->
                when(event) {
                    is Event.NavigateTo -> {
                        Toast.makeText(context, R.string.log_out_success, Toast.LENGTH_SHORT).show()
                        navigateToRoute(event.route)
                    }
                    is Event.ShowToast -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}