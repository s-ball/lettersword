/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

 import androidx.lifecycle.ViewModel
 import androidx.lifecycle.ViewModelProvider
 import kotlinx.coroutines.flow.MutableStateFlow
 import kotlinx.coroutines.flow.asStateFlow
 import kotlinx.coroutines.flow.update
 import org.s_ball.lettersword.data.IWordsRepository
 import org.s_ball.lettersword.domain.Searcher

data class WordsUiState (
    val mask: String = "",
    val wordList: List<String> = listOf()
)

class WordsViewModel(
    val repository: IWordsRepository
): ViewModel() {
    private var _uiState = MutableStateFlow(WordsUiState())
    val uiState = _uiState.asStateFlow()

    private var _letters = MutableStateFlow("")
    val letters = _letters.asStateFlow()

    private var searcher: Searcher? = null

    fun onMaskChange(word: String) {
        val lst = if (searcher != null && word.isNotEmpty())
            searcher!!.masked(word) else listOf()
        _uiState.update { state ->
            WordsUiState(word, lst)
        }
    }
    fun onLettersChange(word: String) {
        _letters.update { w -> word }
        searcher = if (word.isNotEmpty()) Searcher(word, repository) else null
        onMaskChange("")
    }
    companion object Factory: ViewModelProvider.Factory {
        private lateinit var repo: IWordsRepository

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return WordsViewModel(repo) as T
        }

        fun initialize(repository: IWordsRepository) {
            repo = repository
        }
    }
}