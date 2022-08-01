package com.yusifmammadov.samplechatapp.presentation.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yusifmammadov.samplechatapp.ui.theme.SampleChatAppTheme


@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    messageValue: String,
    onSendMessage: () -> Unit,
    onMessageValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Row() {

        TextField(
            value = messageValue,
            onValueChange = {
                onMessageValueChanged(it)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions {
                    onSendMessage()
                    focusManager.clearFocus()
                },
            modifier = modifier
                .weight(1f)
        )
        
        Button(
            onClick = {
                onSendMessage()
                focusManager.clearFocus()
            },
            enabled = messageValue.isNotBlank(),
            modifier = modifier
                .height(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "send message"
            )
        }

        
    }

}

@Composable
@Preview(showSystemUi = true)
fun MessageInputPreview() {
    SampleChatAppTheme {
        MessageInput(
            messageValue = "",
            onSendMessage = {},
            onMessageValueChanged = {})
    }
}