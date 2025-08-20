/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WordsView(
    modifier: Modifier = Modifier,
    model: WordsViewModel = viewModel(factory = WordsViewModel.Factory)
) {
    WordsLayout(
        modifier = modifier,
        letters = model.letters,
        onLettersChange = { word -> model.onLettersChange(word) },
        uiStateFlow = model.uiState,
        onMaskChange = { word -> model.onMaskChange(word)}
    )
}