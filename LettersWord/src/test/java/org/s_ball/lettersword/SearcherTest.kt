package org.s_ball.lettersword

import org.s_ball.lettersword.data.IWordsRepository
import org.s_ball.lettersword.domain.Searcher
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SearcherTest {
    object TestRepo: IWordsRepository {
        override fun words(sz: Int): List<String> {
            return when (sz) {
                3 -> listOf("abc", "abd", "bac", "bad")
                4 -> listOf("abac")
                else -> listOf()
            }
        }
    }
    @Test
    fun all_words() {
        val searcher = Searcher("abcd",TestRepo)
        assertEquals(4, searcher.all(3).size)
    }
    @Test
    fun filtered_words() {
        val searcher = Searcher("abc",TestRepo)
        assertEquals(2, searcher.all(3).size)
    }
    @Test
    fun masked_all_words() {
        val searcher = Searcher("abcd",TestRepo)
        assertEquals(2, searcher.masked("a__").size)
    }
    @Test
    fun masked_filtered_words() {
        val searcher = Searcher("abc",TestRepo)
        assertEquals(1, searcher.masked("a__").size)
    }
    @Test
    fun repeated() {
        val searcher = Searcher("abc", repository = TestRepo)
        assertEquals(0, searcher.all(4).size)
    }
}