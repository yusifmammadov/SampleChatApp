package com.yusifmammadov.samplechatapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.yusifmammadov.samplechatapp.navigation.ChatNavHost
import com.yusifmammadov.samplechatapp.ui.theme.SampleChatAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleChatAppTheme {
                ChatApp()
            }
        }
    }
}


@Composable
fun ChatApp() {
    val navController = rememberNavController()

    ChatNavHost(navController = navController)
}