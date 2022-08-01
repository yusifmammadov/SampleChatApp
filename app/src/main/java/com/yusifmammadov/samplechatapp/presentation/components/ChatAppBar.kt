package com.yusifmammadov.samplechatapp.presentation.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yusifmammadov.samplechatapp.R

@Composable
fun ChatAppBar(
    title: String,
    onSignOut: () -> Unit
) {

    var expandedMenu by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            expandedMenu = !expandedMenu
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = {
                    expandedMenu = !expandedMenu
                }) {

                DropdownMenuItem(
                    onClick = {
                        onSignOut()
                        expandedMenu = !expandedMenu
                    }) {
                    Text(text = stringResource(id = R.string.sign_out))
                }
            }
        }
    )

}