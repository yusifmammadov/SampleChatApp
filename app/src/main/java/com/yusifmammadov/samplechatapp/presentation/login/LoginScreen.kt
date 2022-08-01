package com.yusifmammadov.samplechatapp.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.*
import com.yusifmammadov.samplechatapp.R
import com.yusifmammadov.samplechatapp.navigation.Screen
import com.yusifmammadov.samplechatapp.presentation.components.ProgressBar
import com.yusifmammadov.samplechatapp.ui.theme.SampleChatAppTheme
import com.yusifmammadov.samplechatapp.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.format.TextStyle

const val LOG_TAG = "LoginScreen"


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    if (state.isLoading) {
        ProgressBar()
    }

    Column(
        modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.login),
            fontSize = 20.sp,
            fontWeight = Bold,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
        )
        
        OutlinedTextField(
            value = state.emailValue,
            onValueChange = {
                viewModel.onEmailValueChanged(it)
        },
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            maxLines = 1,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp))

        OutlinedTextField(
            value = state.passwordValue,
            onValueChange = {
                viewModel.onPasswordValueChanged(it)
            },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp))


        Button(
            onClick = {
                      viewModel.signIn()
            },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp)) {
            Text(text = stringResource(id = R.string.login))
        }

        Button(
            onClick = {
                navigateToRoute(Screen.Registration.route)
            },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)) {
            Text(text = stringResource(id = R.string.sign_up))
        }
    }

    LaunchedEffect(viewModel, lifecycle) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.eventChannel.collect{ event ->
                    when(event) {
                        is Event.NavigateTo -> {
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
}

@Composable
@Preview(showSystemUi = true)
fun LoginScreenPreview() {
    SampleChatAppTheme() {
        LoginScreen(navigateToRoute = {})
    }
}