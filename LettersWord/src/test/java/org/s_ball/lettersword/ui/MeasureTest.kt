/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MeasureTest {
    val maxWidth = 704.dp
    var layouts: List<GridLayout> = listOf()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun singleList() {
        val wordList = listOf(listOf("act", "art", "car", "cat", "rat", "tar"))
        composeTestRule.setContent {
            NElt(maxWidth, wordList, 64.dp) { layouts = it }
        }
        Assert.assertEquals(1, layouts.size)
        Assert.assertTrue(layouts[0].width > 64.dp)
    }

    @Test
    fun multiList() {
        val wordList = listOf(
            listOf("act", "art", "car", "cat", "rat", "tar"),
            listOf("cart"))
        composeTestRule.setContent {
            NElt(maxWidth, wordList, 0.dp) { layouts = it }
        }
        Assert.assertEquals(wordList.size, layouts.size)
        Assert.assertEquals(
            layouts[1].width.value * wordList[0][0].length,
            layouts[0].width.value * wordList[1][0].length,
            0.1f
        )
    }
}