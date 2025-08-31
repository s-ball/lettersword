/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.s_ball.lettersword.R


fun doCase(txt: String): String {
    return txt.lowercase()
}

typealias LettersDialogType= @Composable (
    orig: String,
    closer: () -> Unit,
    modifier: Modifier,
    previewMsg: String,
    onValid: (String) -> Unit,
) -> Unit

@Composable
fun LettersDialog (
    orig: String,
    closer: () -> Unit,
    modifier: Modifier = Modifier,
    previewMsg: String = "",
    onValid: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf(previewMsg) }
    val context = LocalContext.current
    
    fun doClose() {
        if (orig.isEmpty()) {
            msg = context.getString(R.string.available_letters_cannot_be_empty)
        }
        else {
            msg = ""
            closer()
        }
    }
    fun doValid(text: String) {
        if (text.isEmpty()) {
            msg = context.getString(R.string.available_letters_cannot_be_empty)
        }
        else {
            onValid(text)
            closer()
        }
    }
    Dialog(
        onDismissRequest = {
        }
    ) {
        OutlinedCard(
            border = BorderStroke(
                width = 1.dp, color = Color.Black
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                TextField(
                    value = text,
                    label = {
                        Text(text = context.getString( R.string.available_letters))
                    },
                    onValueChange = {
                        val t = cleanSpace(it)
                        if ('\r' in it || '\n' in it) {
                            doValid(text)
                        }
                        else if (t.find { c ->  (!c.isLetter() || c.code >= 128) } != null) {
                            msg = context.getString(R.string.only_letters_are_allowed)
                        }
                        else if (text != t){
                            msg = ""
                            text = doCase(t)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                        .testTag("LettersField")
                )
                if (msg != "") {
                    Text(
                        msg,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .testTag("Message"),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            doValid(text)
                        },
                        modifier= Modifier.testTag("Ok")
                    ) {
                        Text(context.getString(R.string.ok))
                    }
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = { doClose() },
                        modifier = Modifier.testTag("Cancel")
                    ) {
                        Text(context.getString(R.string.cancel))
                    }
                }
            }
        }
    }
}

fun cleanSpace(orig: String): String {
    var spacePos: Int = -1
    orig.forEachIndexed { ix, c ->
        if (c == ' ') {
            if (spacePos == -1) {
                spacePos = ix
            }
            else return " "
        }
    }
    if (spacePos == -1) return orig
    return orig.removeRange(spacePos, spacePos + 1)
}

@Preview(showBackground = true, apiLevel = 34)
@Preview(showBackground = true, apiLevel = 34, device = "id:small_phone")
@Preview(showBackground = true, apiLevel = 34, device = "id:medium_tablet")
@Composable
fun DialogPreview() {
    LettersDialog("cart", closer = {}, onValid = { }, previewMsg ="foo")
}