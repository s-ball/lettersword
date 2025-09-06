package org.s_ball.lettersword

import org.s_ball.lettersword.data.IWordsRepository
import org.s_ball.lettersword.ui.WordsViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ViewModelTest {
    object TestRepo: IWordsRepository {
        override fun words(sz: Int): List<String> {
            return when (sz) {
                3 -> listOf("abc", "abd", "bac", "bad")
                4 -> listOf("abcd")
                else -> listOf()
            }
        }
    }

    @Test
    fun defaultView() {
        val view = WordsViewModel(TestRepo)
        assertEquals("", view.letters.value)
        assertEquals("", view.uiState.value.mask)
        assert(view.uiState.value.wordList.isEmpty())
    }

    @Test
    fun changeAll() {
        val view = WordsViewModel(TestRepo)
        view.onLettersChange("abcabc")
        view.onMaskChange("a__")
        assertEquals(1, view.uiState.value.wordList.size)
        assertEquals(listOf("abc"), view.uiState.value.wordList[0])
    }

    @Test
    fun resetLetters() {
        val view = WordsViewModel(TestRepo)
        view.onLettersChange("abcabc")
        view.onMaskChange("a__")
        view.onLettersChange("abdabe")
        assertEquals("abdabe", view.letters.value)
        assertEquals("", view.uiState.value.mask)
        assert(view.uiState.value.wordList.isEmpty())
    }
}