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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun WordsLayout(modifier: Modifier = Modifier, //model: WordsViewModel = viewModel(),
                uiStateFlow: StateFlow<WordsUiState>,
                onMaskChange: (String) -> Unit,
                letters: String,
                onLettersChange: (String) -> Unit,
                previewMsg: String = "") {
    var lettersShow by rememberSaveable {
        mutableStateOf(false)
    }
    val uiState by uiStateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val dpSaver = Saver<MutableState<Dp>, MutableState<Float>>(
        save = { it -> mutableFloatStateOf(it.value.value) },
        restore = { it -> mutableStateOf(it.value.dp) }
    )

    var msg by rememberSaveable { mutableStateOf(previewMsg) }
    var mask by rememberSaveable { mutableStateOf(uiState.mask) }
    var width by rememberSaveable(saver = dpSaver) { mutableStateOf(75.dp) }
    var newList by rememberSaveable { mutableStateOf(false) }

    fun doMaskChange(word: String) {
        onMaskChange(word)
        newList = true
    }
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (letters.isEmpty()) lettersShow = true
        if (lettersShow) LettersDialog(
            letters,
            closer = { lettersShow = false }
        ) { text: String ->
            onLettersChange(text)
            mask = ""
        }
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
                                    doMaskChange(mask)
                                    msg = ""
                                } else if (t.find { c -> ((!c.isLetter() && c != '_') || c.code >= 128) } != null) {
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
                            onClick = { doMaskChange(mask) },
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
            if (newList) {
                DoMeasures(mask) { width = it }
                newList = false
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = width + 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("WordsList")
            ) {
                items(uiState.wordList) { word ->
                    Text(word, modifier = Modifier.padding(start = 12.dp))

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
            letters = "cart",
            onLettersChange = {},
            uiStateFlow = MutableStateFlow(
                WordsUiState(
                    "___",
                    listOf("act", "art", "car", "cat", "rat", "tar")
                )
            ),
            onMaskChange = {},
            previewMsg = "Error message",
            modifier = Modifier.padding(it),
        )
    }
}