package com.yusifmammadov.samplechatapp.presentation.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.*
import com.yusifmammadov.samplechatapp.R
import com.yusifmammadov.samplechatapp.ui.theme.SampleChatAppTheme
import java.time.format.TextStyle

const val LOG_TAG = "LoginScreen"


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {

    var emailFieldState by rememberSaveable {
        mutableStateOf("")
    }

    var passwordFieldState by rememberSaveable {
        mutableStateOf("")
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
            value = emailFieldState,
            onValueChange = {
                emailFieldState = it
        },
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp))

        OutlinedTextField(
            value = passwordFieldState,
            onValueChange = {
                passwordFieldState = it
            },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp))


        Button(
            onClick = { /*TODO*/ },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)) {
            Text(text = stringResource(id = R.string.login))
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)) {
            Text(text = stringResource(id = R.string.sign_up))
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