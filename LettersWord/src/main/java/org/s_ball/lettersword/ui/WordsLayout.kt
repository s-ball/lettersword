/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.s_ball.lettersword.R
import org.s_ball.lettersword.ui.theme.Typography

data class GridLayout(val nElt: Int, val width: Dp)

val lettersDialog: LettersDialogType = {
        orig,
        closer,
        modifier,
        previewMsg,
        onValid,
    -> LettersDialog(orig, closer, modifier, previewMsg, onValid)}

@Composable
fun WordsLayout(modifier: Modifier = Modifier, //model: WordsViewModel = viewModel(),
                uiStateFlow: StateFlow<WordsUiState>,
                onMaskChange: (String) -> Boolean,
                lettersFlow: StateFlow<String>,
                onLettersChange: (String) -> Unit,
                previewMsg: String = "",
                dialog: LettersDialogType = lettersDialog
) {
    var lettersShow by rememberSaveable {
        mutableStateOf(false)
    }
    val uiState by uiStateFlow.collectAsStateWithLifecycle()
    val letters by lettersFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var msg by rememberSaveable { mutableStateOf(previewMsg) }
    var mask by rememberSaveable { mutableStateOf(uiState.mask) }

    fun doMaskChange(word: String): String {
        val msg = if (onMaskChange(word)) ""
        else context.getString(R.string.only_letters_or__are_allowed)
        return msg
    }
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (letters.isEmpty()) lettersShow = true
        if (lettersShow) dialog(
            letters,
            { lettersShow = false },
            Modifier,
            "",
            { text: String ->
                onLettersChange(text)
                mask = ""
            }
        )
        OutlinedCard(
            border = BorderStroke(
                width = 1.dp, color = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    stringResource(R.string.available_letters),
                    style = Typography.labelSmall,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            letters,
                            style = Typography.displaySmall,
                            modifier = Modifier.testTag("letters")
                        )
                    }
                    OutlinedButton(
                        onClick = {
                            lettersShow = true
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = stringResource(R.string.change),
                        )
                    }
                }
            }
        }
        if (!letters.isEmpty()) {
            OutlinedCard(
                border = BorderStroke(
                    width = 1.dp, color = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)

                ) {
                    Text(
                        stringResource(R.string.mask_to_search),
                        style = Typography.labelSmall
                    )
                    Row {
                        TextField(
                            value = mask,
                            onValueChange = {
                                val t = cleanSpace(it)
                                if ('\r' in it || '\n' in it) {
                                    msg = doMaskChange(mask)
                                } else if (t.find { c ->
                                        ((!c.isLetter() && (c != '_') && (c != '*'))
                                                || c.code >= 128)
                                    } != null) {
                                    msg = context.getString(R.string.only_letters_or__are_allowed)
                                } else if (mask != t) {
                                    msg = ""
                                    mask = doCase(t)
                                }
                            },
                            textStyle = Typography.displaySmall,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .testTag("MaskField")
                        )
                        OutlinedButton(
                            onClick = { msg = doMaskChange(mask) },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .testTag("MaskButton")
                        ) {
                            Text(
                                stringResource(R.string.search),
                            )
                        }
                    }
                    if (msg != "") {
                        Text(
                            msg,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
            var pixWidth by remember { mutableIntStateOf(0) }
            var maxWidth by remember { mutableStateOf(0.dp) }
            var layouts by remember { mutableStateOf(listOf<GridLayout>()) }
            if (!uiState.wordList.isEmpty()) {
                LazyColumn(
                    modifier = Modifier.testTag("WordsList")
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            pixWidth = coordinates.size.width
                        }
                ) {
                    if (pixWidth != 0) {
                        item {
                            maxWidth = with(LocalDensity.current) {
                                pixWidth.toDp()
                            }
                            // Text("$pixWidth pixels - $maxWidth dp")
                            NElt(maxWidth, uiState.wordList, 4.dp) {
                                layouts = it
                            }
                        }
                        itemsIndexed(uiState.wordList) { i, lst ->
                            if (!lst.isEmpty()) {
                                val layout = layouts[i]
                                Column(modifier = Modifier.fillMaxWidth()
                                    .padding(4.dp)) {
                                    for (row in lst.chunked(layout.nElt)) {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            for (w in row) {
                                                Text(w, modifier = Modifier.width(layout.width))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, apiLevel = 34)
@Preview(showBackground = true, apiLevel = 34, device = "id:small_phone")
@Preview(showBackground = true, apiLevel = 34, device = "id:medium_tablet")
@Composable
fun WordsPreview() {

    Scaffold {
        WordsLayout(
            lettersFlow = MutableStateFlow("cart"),
            onLettersChange = {},
            uiStateFlow = MutableStateFlow(
                WordsUiState(
                    "___",
                    listOf(listOf("act", "art", "car", "cat", "rat", "tar"))
                )
            ),
            onMaskChange = { w -> true },
            previewMsg = "Error message",
            modifier = Modifier.padding(it),
            dialog = lettersDialog
        )
    }
}