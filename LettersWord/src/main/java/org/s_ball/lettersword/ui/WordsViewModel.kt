package org.s_ball.lettersword.ui

 import androidx.compose.runtime.getValue
 import androidx.compose.runtime.mutableStateOf
 import androidx.compose.runtime.setValue
 import androidx.lifecycle.ViewModel
 import kotlinx.coroutines.flow.MutableStateFlow
 import kotlinx.coroutines.flow.asStateFlow
 import kotlinx.coroutines.flow.update
 import org.s_ball.lettersword.RepoHolder
 import org.s_ball.lettersword.data.IWordsRepository
 import org.s_ball.lettersword.domain.Searcher

data class WordsUiState (
    val mask: String = "",
    val wordList: List<String> = listOf()
)

class WordsViewModel(
    val repository: IWordsRepository = RepoHolder.getRepo()
): ViewModel() {
    private var _uiState = MutableStateFlow(WordsUiState())
    val uiState = _uiState.asStateFlow()

    var letters by mutableStateOf("")
        private set

    private var searcher: Searcher? = null

    fun onMaskChange(word: String) {
        val lst = if (searcher != null && word.isNotEmpty())
            searcher!!.masked(word) else listOf()
        _uiState.update { state ->
            WordsUiState(word, lst)
        }
    }
    fun onLettersChange(word: String) {
        letters = word
        searcher = if (word.isNotEmpty()) Searcher(word, repository) else null
        onMaskChange("")
    }
}