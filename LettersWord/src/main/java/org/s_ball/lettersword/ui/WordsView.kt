package org.s_ball.lettersword.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.s_ball.lettersword.R
import org.s_ball.lettersword.data.WordsRepository
import org.s_ball.lettersword.ui.theme.LettersWordTheme
import org.s_ball.lettersword.ui.theme.Typography
import java.io.ByteArrayInputStream

@Composable
fun WordsView(model: WordsViewModel, modifier: Modifier = Modifier,
              previewMsg: String = "") {
    var lettersShow by remember {
        mutableStateOf(false)
    }
    var mask by remember { mutableStateOf(model.uiState.mask) }
    var listOk by remember { mutableStateOf(false) }
    var msg by remember { mutableStateOf(previewMsg) }
    val context = LocalContext.current

    fun doMaskChange(mask: String) {
        model.onMaskChange(mask)
        listOk = true
    }
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        if (model.uiState.letters.isEmpty()) lettersShow = true
        if (lettersShow) LettersDialog(
            model.uiState.letters,
            closer = { lettersShow = false }
        ) { text: String ->
            model.onLettersChange(text)
            mask = ""
        }
        OutlinedCard(
            border = BorderStroke(
                width = 1.dp, color = Color.Black
            ),
            modifier = modifier.fillMaxWidth()
        ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
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
                                model.uiState.letters,
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
        if (!model.uiState.letters.isEmpty()) {
            var width by remember { mutableStateOf(75.dp) }

            OutlinedCard(
                border = BorderStroke(
                    width = 1.dp, color = Color.Black
                ),
                modifier = modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)

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
                                }
                                else if (t.find { c ->  ((!c.isLetter() && c != '_') || c.code >= 128) } != null) {
                                    msg = context.getString(R.string.only_letters_or__are_allowed)
                                }
                                else if (mask != t){
                                    msg = ""
                                    mask = t
                                    listOk = false
                                }
                            },
                            textStyle = Typography.displayMedium,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )
                        OutlinedButton(
                            onClick = { model.onMaskChange(mask) },
                            modifier = Modifier.align(Alignment.CenterVertically)
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
            if (listOk) {
                Measures(mask) { width = it }
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = width),
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                ) {
                items(model.uiState.wordList.value) { word ->
                    Text(word, modifier = Modifier.padding(start = 12.dp))

                }
            }
            /*
            LazyColumn (modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()){
                items(model.uiState.wordList.value) { word ->
                    Text(word, modifier = Modifier.padding(start = 12.dp))
                }
            }
            */
        }
    }
}


@Preview(showBackground = true, apiLevel = 34)
@Preview(showBackground = true, apiLevel = 34, device = "id:small_phone")
@Preview(showBackground = true, apiLevel = 34, device = "id:medium_tablet")
@Composable
fun WordsPreview() {
    val repo = WordsRepository(
        ByteArrayInputStream(
            "act\nart\ncar\ncat\ncart\rat\ntac\ntar\n".toByteArray()
        )
    )
    val model = WordsViewModel(repo)
    model.onLettersChange("trac")
    model.onMaskChange("___")
    LettersWordTheme {
        WordsView(model, previewMsg = "Error message")
    }
}