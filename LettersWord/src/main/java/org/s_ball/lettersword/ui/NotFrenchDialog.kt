/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.s_ball.lettersword.R

@Composable
fun NotFrenchDialog(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onAbort: () -> Unit)  {
    AlertDialog(
        confirmButton = {
            Button(
                onClick = onClose
            ) {
                Text(stringResource(R.string.cont))
            }
        },
        onDismissRequest = { },
        modifier = modifier,
        dismissButton = {
            Button(
                onClick = {
                    onClose()
                    onAbort()
                }
            ) {
                Text(stringResource(R.string.abort))
            }
        },
        title = {
            Text(stringResource(R.string.beware_wrong_language))
        },
        text = {
            Column {
                Text(stringResource(R.string.the_current_dictionary_only_contains_french_words))
                Text(stringResource(R.string.do_you_really_want_to_continue))
            }
        },
    )
}

@Preview()
@Composable
fun AlertPreview() {
   NotFrenchDialog {  }
}