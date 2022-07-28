package com.yusifmammadov.samplechatapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusifmammadov.samplechatapp.data.model.User

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    userName: String
) {

    Box(modifier = modifier
        .fillMaxWidth()) {

        Text(
            text = userName,
            fontSize = 20.sp,
            modifier = modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )
    }
}