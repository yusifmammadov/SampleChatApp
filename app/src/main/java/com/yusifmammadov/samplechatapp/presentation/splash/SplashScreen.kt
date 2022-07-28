package com.yusifmammadov.samplechatapp.presentation.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yusifmammadov.samplechatapp.R
import com.yusifmammadov.samplechatapp.navigation.Screen
import com.yusifmammadov.samplechatapp.ui.theme.SampleChatAppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
        .fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = modifier
                .align(Alignment.CenterHorizontally)

        )
    }
    
    LaunchedEffect(Unit) {

        val route = if(viewModel.isUserSignedIn) Screen.Home.route
        else Screen.Login.route

        delay(500)
        navigateToRoute(route)
    }


}

@Composable
@Preview(showSystemUi = true)
fun SplashScreenPreview() {
    SampleChatAppTheme() {
        SplashScreen(navigateToRoute = {})
    }
}