/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.junit.Assert
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

    var onValidRef: (String) -> Unit = {}
    var closerRef: () -> Unit = {}
    var nbCalls = 0
    val tag = "stub"
    val foo = "foo"
    val stubDialog: LettersDialogType = {
            orig,
            closer,
            modifier,
            previewMsg,
            onValid,
        ->
        onValidRef = onValid
        closerRef = closer
        nbCalls += 1
        Text("STUB", modifier = Modifier.testTag(tag))
    }

    @Before
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
        uiState = MutableStateFlow(WordsUiState())
        lettersFlow = MutableStateFlow("")
        clearMocks(commands)
    }

    fun init(letters: String,
             mask: String = "",
             words: List<List<String>> = listOf(),
    ) {
        uiState = MutableStateFlow(WordsUiState(mask, words))
        lettersFlow = MutableStateFlow(letters)
        val lettersSlot = slot<String>()
        every { commands.onLettersChange(capture(lettersSlot))
        } answers { lettersFlow.update { words -> lettersSlot.captured }}
        composeTestRule.setContent {
            WordsLayout(
                Modifier,
                uiState.asStateFlow(),
                onMaskChange = { txt ->
                    commands.onMaskChange(txt)
                    true
                },
                lettersFlow = lettersFlow,
                onLettersChange = { txt -> commands.onLettersChange(txt)},
                dialog = stubDialog
            )
        }
    }

    @Test
    fun initialEmptyLetters() {
        init("")
        composeTestRule.onNodeWithTag(tag).assertIsDisplayed()
    }
    @Test
    fun initialLetters() {
        init("letters")
        composeTestRule.onNodeWithTag(tag).assertDoesNotExist()
    }
    @Test
    fun setLetters() {
        init("")
        composeTestRule.onNodeWithTag(tag).assertIsDisplayed()
        Assert.assertEquals(1, nbCalls)
        verify (exactly = 0){ commands.onLettersChange(foo) }
        onValidRef(foo)
        closerRef()
        verify (exactly = 1){ commands.onLettersChange(foo) }
        Assert.assertEquals(1, nbCalls)
        composeTestRule.onNodeWithTag(tag).assertDoesNotExist()
    }

    @Test
    fun changeMask() {
        init("letter")
        composeTestRule.onNodeWithTag("MaskField").performTextInput("_e_")
        composeTestRule.onNodeWithTag("WordsList").assertDoesNotExist()
        composeTestRule.onNodeWithTag("MaskButton").performClick()
        verify { commands.onMaskChange("_e_") }
        uiState.update { state -> WordsUiState("_e_",
            listOf(listOf("let", "ree", "tee", "tel", "ter", "tet"))) }
        composeTestRule.onNodeWithTag("WordsList").onChildren().assertCountEquals(6)
    }
}