package org.s_ball.lettersword.domain

import org.s_ball.lettersword.data.IWordsRepository
import kotlin.text.iterator

class Searcher(val letters: String, private val repository: IWordsRepository) {
    fun all(sz: Int): List<String> {
        val cr: MutableList<String> = mutableListOf()
        for (word in repository.words(sz)) {
            val copy = letters.toCharArray()
            var ok = true
            for (c in word) {
                val ix = copy.indexOf(c)
                if (ix == -1) {
                    ok = false
                    break
                }
                else copy[ix] = Char(0)
            }
            if (ok) {
                cr.add(word)
            }
        }
        return cr
    }

    fun masked(mask: String): List<String> {
        val cr: MutableList<String> = mutableListOf()
        for (word in repository.words(mask.length)) {
            val copy = letters.toCharArray()
            var ok = true
            for ((i, c) in mask.withIndex()) {
                val ix = copy.indexOf(word[i])
                if ((ix == -1)
                    || ((c != '_') && (c != word[i]))) {
                    ok = false
                    break
                }
                else copy[ix] = Char(0)
            }
            if (ok) {
                cr.add(word)
            }
        }
        return cr
    }
}