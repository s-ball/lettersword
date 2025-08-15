/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.s_ball.lettersword.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBar(){
    var info by rememberSaveable { mutableStateOf(false) }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                IconButton(onClick = { info = true }) {
                    Icon(Icons.Default.Info, contentDescription = "Information")
                }
            }
        }
    )
    if (info) {
        InfoDialog { info = false }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Preview(showBackground = true, apiLevel = 34, device = "id:small_phone")
@Preview(showBackground = true, apiLevel = 34, device = "id:small_phone", widthDp = 640, heightDp = 360)
@Preview(showBackground = true, apiLevel = 34, device = "id:medium_tablet")
@Composable
fun TopBarPreview() {
    Scaffold(
        topBar = {
            MenuBar()
        },
    ) {
        Text("Content text",
            modifier = Modifier.padding(it)
        )
    }
}