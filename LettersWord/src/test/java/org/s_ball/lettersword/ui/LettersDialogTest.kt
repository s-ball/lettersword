/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.called
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog

@RunWith(AndroidJUnit4::class)
class LettersDialogTest {
    interface Commands {
        fun onValid(txt: String)
        fun closer()
    }
    val cmd: Commands = mockk<Commands>(relaxUnitFun = true)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun resetMocks() {
        clearMocks(cmd)
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    fun initContent(orig: String = "") {
        composeTestRule.setContent {
            LettersDialog(
                orig,
                closer = { cmd.closer() },
                onValid = { txt -> cmd.onValid(txt) }
            )
        }

    }
    @Test
    fun ensureLettersOk() {
        initContent()
        val txt = "foo"
        composeTestRule.onNodeWithTag("LettersField").performTextInput(txt)
        composeTestRule.onNodeWithTag("LettersField").performTextInput(txt)
        composeTestRule.onNodeWithTag("LettersField").assertTextContains("$txt$txt")
    }

    @Test
    fun ensureLettersOnly() {
        val txt = "foo"
        initContent()
        composeTestRule.onNodeWithTag("LettersField").performTextInput(txt)
        composeTestRule.onNodeWithTag("Message").assertDoesNotExist()
        composeTestRule.onNodeWithTag("LettersField").performTextInput("_")
        composeTestRule.onNodeWithTag("Message").assertIsDisplayed()
        composeTestRule.onNodeWithTag("LettersField").assertTextContains(txt)
        verify { cmd wasNot called }
    }

    @Test
    fun clickOk() {
        initContent()
        val txt = "foo"
        composeTestRule.onNodeWithTag("LettersField").performTextInput(txt)
        composeTestRule.onNodeWithTag("Ok").performClick()
        verify(exactly = 1) { cmd.onValid(txt) }
        verify(exactly = 1) { cmd.closer() }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun useEnter() {
        initContent()
        val txt = "foo"
        composeTestRule.onNodeWithTag("LettersField").performTextInput(txt)
        composeTestRule.onNodeWithTag("LettersField").performKeyInput {
            keyDown(Key.Companion.Enter)
            keyUp(Key.Companion.Enter)
        }
        verify(exactly = 1) { cmd.onValid(txt) }
        verify(exactly = 1) { cmd.closer() }
    }

    @Test
    fun abortValidOrig() {
        val orig = "bar"
        initContent(orig)
        val txt = "foo"
        composeTestRule.onNodeWithTag("LettersField").performTextInput(txt)
        composeTestRule.onNodeWithTag("Cancel").performClick()
        verify(exactly = 0) { cmd.onValid(allAny()) }
        verify(exactly = 1) { cmd.closer() }
    }

    @Test
    fun abortEmptyOrig() {
        initContent("")
        val txt = "foo"
        composeTestRule.onNodeWithTag("LettersField").performTextInput(txt)
        composeTestRule.onNodeWithTag("Cancel").performClick()
        verify { cmd wasNot called }
    }
}