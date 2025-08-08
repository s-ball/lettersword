package org.s_ball.lettersword.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.s_ball.lettersword.data.WordsRepository
import org.s_ball.lettersword.ui.theme.LettersWordTheme
import org.s_ball.lettersword.ui.theme.Typography
import java.io.ByteArrayInputStream

@Composable
fun WordsView(model: WordsViewModel, modifier: Modifier = Modifier) {
    var lettersShow by remember {
        mutableStateOf(false)
    }
    var mask by remember { mutableStateOf(model.uiState.mask) }
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
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        "Available Letters :",
                        style = Typography.labelSmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.padding(0.dp)
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
                                text = "Change",
                            )
                        }
                    }
                }
        }
        if (!model.uiState.letters.isEmpty()) {
            OutlinedCard(
                border = BorderStroke(
                    width = 1.dp, color = Color.Black
                ),
                modifier = modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp)

                ) {
                    Text(
                        "Mask to search (use _ for unknown - eg _a_) :",
                        style = Typography.labelSmall
                    )
                    Row {
                        TextField(
                            value = mask,
                            onValueChange = {
                                mask = it
                            },
                            textStyle = Typography.displayMedium,
                            modifier = Modifier.weight(1f)
                                .padding(end = 8.dp)
                        )
                        OutlinedButton(
                            onClick = { model.onMaskChange(mask) },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text("Search",
                            )
                        }
                    }
                }
            }
            LazyColumn (modifier = modifier.padding(8.dp).fillMaxWidth()){
                items(model.uiState.wordList.value) { word ->
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
    val repo = WordsRepository(
        ByteArrayInputStream(
            "act\nart\ncar\ncat\ncart\rat\ntac\ntar\n".toByteArray()
        )
    )
    val model = WordsViewModel(repo)
    model.onLettersChange("trac")
    model.onMaskChange("___")
    LettersWordTheme {
        WordsView(model)
    }
}