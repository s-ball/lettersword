/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp

@Composable
fun NElt(maxWidth: Dp, wordLists: List<List<String>>, delta: Dp, ret: (List<GridLayout>) -> Unit) {
    val measurer = rememberTextMeasurer()
    val lst = wordLists.map { ls ->
        val sz = if (ls.isEmpty()) 1 else ls[0].length
        val width = with(LocalDensity.current) {
            measurer.measure("m".repeat(sz)).size.width.toDp() + delta
        }
        var n = (maxWidth / width).toInt()
        if (n == 0) n = 1
        GridLayout(n, maxWidth / n)
    }
    ret(lst)
}