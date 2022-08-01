package com.yusifmammadov.samplechatapp.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yusifmammadov.samplechatapp.data.model.Message
import com.yusifmammadov.samplechatapp.data.model.MessageUi
import com.yusifmammadov.samplechatapp.ui.theme.SampleChatAppTheme

@Composable
fun ChatContent(
    modifier: Modifier = Modifier,
    messages: List<MessageUi>,
    messageValue: String,
    onMessageValueChanged: (String) -> Unit,
    onSendMessage: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier
                .weight(1f)

        ) {
            items(messages.size) { messageUi ->
                MessageItem(messageUi = messages[messageUi])
            }
        }

        MessageInput(
            modifier = modifier,
            messageValue = messageValue,
            onSendMessage = { onSendMessage() },
            onMessageValueChanged = {
                onMessageValueChanged(it)
            })
    }


}

@Composable
@Preview(showSystemUi = true)
fun ChatContentPreview() {
    SampleChatAppTheme() {
        ChatContent(messages = emptyList(), messageValue = "", onSendMessage = {}, onMessageValueChanged = {})
    }
}