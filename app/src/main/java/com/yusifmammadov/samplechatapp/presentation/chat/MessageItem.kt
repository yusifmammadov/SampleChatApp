package com.yusifmammadov.samplechatapp.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.yusifmammadov.samplechatapp.data.model.MessageUi

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    messageUi: MessageUi
) {

    val isMine = messageUi.isMine

    Column(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
        
        Card(
            modifier = modifier
                .widthIn(max = 300.dp),
            shape = shapeForMessageItem(isMine = isMine),
            backgroundColor = if (isMine) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        ) {
            Text(
                modifier = modifier
                    .padding(12.dp),
                text = messageUi.message,
                color = if (isMine) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary)
        }

    }
}

@Composable
fun shapeForMessageItem(isMine: Boolean): Shape {

    val corners = RoundedCornerShape(16.dp)

    return if(isMine) corners.copy(bottomEnd = CornerSize(0)) else corners.copy(bottomStart = CornerSize(0))
    
}