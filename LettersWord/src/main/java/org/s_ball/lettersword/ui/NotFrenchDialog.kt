package org.s_ball.lettersword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

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
                Text("Continue")
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
                Text("Abort")
            }
        },
        title = {
            Text("Beware: wrong language")
        },
        text = {
            Column {
                Text("The current dictionary only contains French words.")
                Text("Do you really want to continue?")
            }
        },
    )
}

@Preview()
@Composable
fun AlertPreview() {
   NotFrenchDialog {  }
}