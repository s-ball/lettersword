package org.s_ball.lettersword.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.s_ball.lettersword.data.IWordsRepository
import org.s_ball.lettersword.domain.Searcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class WordsUiState (
    var letters: String = "",
    var mask: String = "",
    var wordList: MutableState<List<String>> = mutableStateOf(listOf())
)

class WordsViewModel(private val repository: IWordsRepository): ViewModel() {
    private var _uiState = MutableStateFlow(WordsUiState())
    val uiState = _uiState.asStateFlow().value

    val wordList
        get() = uiState.wordList.value

    private var searcher: Searcher? = null

    fun onMaskChange(word: String) {
        _uiState.value.mask = word
        _uiState.value.wordList.value = if (searcher != null && word.isNotEmpty())
            searcher!!.masked(word) else listOf()
    }
    fun onLettersChange(word: String) {
        _uiState.value.letters = word
        searcher = if (word.isNotEmpty()) Searcher(word, repository) else null
        onMaskChange("")
    }
}