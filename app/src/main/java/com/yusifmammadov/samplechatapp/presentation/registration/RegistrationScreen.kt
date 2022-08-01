package com.yusifmammadov.samplechatapp.presentation.registration

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.yusifmammadov.samplechatapp.R
import com.yusifmammadov.samplechatapp.presentation.components.ProgressBar
import com.yusifmammadov.samplechatapp.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String) -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val lifecyle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current



    if (state.isLoading) {
        ProgressBar()
    }

    Column(
        modifier.fillMaxSize()
    ) {

        Text(
            text = stringResource(id = R.string.sign_up),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
        )

        OutlinedTextField(
            value = state.userNameValue,
            onValueChange = {
                viewModel.onUsernameValueChanged(it)
            },
            label = {
                Text(text = stringResource(id = R.string.username))
            },
            maxLines = 1,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp))

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
                .padding(top = 16.dp))


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
                      viewModel.signUp()
            },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(24.dp)) {
            Text(text = stringResource(id = R.string.sign_up))
        }
    }

    LaunchedEffect(viewModel, lifecyle) {
        lifecyle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.eventChannel.collect{ event ->
                    when(event) {
                        is Event.NavigateTo -> {
                            Toast.makeText(context, R.string.registered_successfully, Toast.LENGTH_SHORT).show()
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