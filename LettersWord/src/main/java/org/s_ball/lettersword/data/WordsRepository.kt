package org.s_ball.lettersword.data

import java.io.InputStream

interface IWordsRepository {
    fun words(sz: Int): List<String>
}
class WordsRepository (inputStream: InputStream): IWordsRepository {
    val info: String
    val maxLen: Int
    val total: Int
    private val _bySize: MutableList<MutableList<String>> = mutableListOf()
    init {
        var count = 0
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
                count += 1
            }
        }
        maxLen = _bySize.size
        total = count
        info = "$count - $maxLen"
    }
    override fun words(sz: Int): List<String> {
        return if (sz <= _bySize.size) _bySize[sz - 1] else listOf()
    }
}