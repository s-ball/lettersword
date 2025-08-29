/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog

@RunWith(AndroidJUnit4::class)
class WordsLayoutTest {
    interface Commands {
        fun onLettersChange(letters: String)
        fun onMaskChange(mask: String)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    val commands = mockk<Commands>(relaxUnitFun = true)
    lateinit var uiState: MutableStateFlow<WordsUiState>
    lateinit var lettersFlow: MutableStateFlow<String>

    @Before
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
        uiState = MutableStateFlow(WordsUiState())
        lettersFlow = MutableStateFlow("")
        clearMocks(commands)
    }

    fun init(letters: String,
             mask: String = "",
             words: List<String> = listOf(),
    ) {
        uiState = MutableStateFlow(WordsUiState(mask, words))
        lettersFlow = MutableStateFlow(letters)
        composeTestRule.setContent {
            WordsLayout(
                Modifier,
                uiState.asStateFlow(),
                onMaskChange = { txt -> commands.onMaskChange(txt) },
                lettersFlow = lettersFlow,
                onLettersChange = { txt -> commands.onLettersChange(txt)},
            )
        }
    }

    @Test
    fun initialEmptyLetters() {
        init("")
        composeTestRule.onNodeWithTag("LettersField").assertIsDisplayed()
    }
    @Test
    fun initialLetters() {
        init("letters")
        composeTestRule.onNodeWithTag("LettersField").assertDoesNotExist()
    }
    @Test
    fun setLetters() {
        init("")
        composeTestRule.onNodeWithTag("LettersField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("LettersField").performTextInput("foo")
        composeTestRule.onNodeWithTag("Ok").performClick()
        verify { commands.onLettersChange("foo") }
        lettersFlow.update { w -> "foo" }
        composeTestRule.onNodeWithTag("LettersField").assertDoesNotExist()
    }

    @Test
    fun changeMask() {
        init("letter")
        composeTestRule.onNodeWithTag("MaskField").performTextInput("_e_")
        composeTestRule.onNodeWithTag("WordsList").onChildren().assertCountEquals(0)
        composeTestRule.onNodeWithTag("MaskButton").performClick()
        verify { commands.onMaskChange("_e_") }
        uiState.update { state -> WordsUiState("_e_",
            listOf("let", "ree", "tee", "tel", "ter", "tet")) }
        composeTestRule.onNodeWithTag("WordsList").onChildren().assertCountEquals(6)
    }
}