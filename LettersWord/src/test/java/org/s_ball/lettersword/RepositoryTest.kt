package org.s_ball.lettersword

import org.s_ball.lettersword.data.WordsRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.s_ball.lettersword.data.IWordsRepository
import java.io.ByteArrayInputStream

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RepositoryTest {
    val stream = ByteArrayInputStream("car\ncat\ncart".toByteArray())
    val repository: IWordsRepository = WordsRepository(stream)
/*
    @Test
    fun maxSize() {
        assertEquals(4, repository.maxLen)
    }
*/
    @Test
    fun words3() {
        assertEquals(2, repository.words(3).size)
    }
    @Test
    fun words4() {
        assertEquals(1, repository.words(4).size)
    }
    @Test
    fun words5() {
        assertEquals(0, repository.words(5).size)
    }
}