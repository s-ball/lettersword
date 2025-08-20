/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.data

import java.io.InputStream

interface IWordsRepository {
    //val maxLen: Int
    //val total: Int
    fun words(sz: Int): List<String>
}
class WordsRepository (inputStream: InputStream): IWordsRepository {
    //override val maxLen: Int
    //override val total: Int
    private val _bySize: MutableList<MutableList<String>> = mutableListOf()
    init {
        // var count = 0
        val reader = inputStream.bufferedReader(Charsets.US_ASCII)
        reader.useLines {
            lines -> lines.forEach {
                val len = it.length
                if (len > _bySize.size) {
                    repeat(len - _bySize.size) {
                        _bySize.add(mutableListOf())
                    }
                }
                _bySize[len - 1].add(it)
                // count += 1
            }
        }
/*
        maxLen = _bySize.size
        total = count
*/
    }
    override fun words(sz: Int): List<String> {
        return if (sz <= _bySize.size) _bySize[sz - 1] else listOf()
    }
}