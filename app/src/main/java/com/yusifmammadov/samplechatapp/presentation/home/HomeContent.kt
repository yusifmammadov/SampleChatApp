package com.yusifmammadov.samplechatapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yusifmammadov.samplechatapp.data.model.User

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    listUsers: List<User>,
    onItemClicked: (String) -> Unit
) {
    LazyColumn(modifier.fillMaxSize()) {

        items(listUsers.size) { i ->
            val user = listUsers[i]
            UserItem(
                userName = user.userName,
                modifier = modifier
                    .clickable {
                        onItemClicked(user.uid)
                    }
            )

            if (i < listUsers.size) {
                Divider()
            }
        }
    }
}