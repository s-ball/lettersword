/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.s_ball.lettersword.MainActivity

@RunWith(AndroidJUnit4::class)
class ActivityTest {
    @get:Rule

    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun initial() {
        composeTestRule.onNodeWithTag("OnlyFrench").assertIsDisplayed()
    }

    @Test
    fun abort() {
        composeTestRule.onNodeWithTag("Abort").performClick()
        composeTestRule.onNodeWithTag("OnlyFrench").assertDoesNotExist()
        composeTestRule.onNodeWithTag("LettersField").assertDoesNotExist()
        var ok: Boolean
        try {
            Assert.assertFalse(
                composeTestRule.activity.lifecycle.currentState.isAtLeast(
                    Lifecycle.State.RESUMED
                )
            )
            ok = true
        }
        catch (_: NullPointerException) {
            ok = true
        }
        @Suppress("KotlinConstantConditions")
        if (!ok) Assert.fail("Activity still active")
    }

    @Test
    fun cont() {
        composeTestRule.onNodeWithTag("Continue").performClick()
        composeTestRule.onNodeWithTag("OnlyFrench").assertDoesNotExist()
        composeTestRule.onNodeWithTag("LettersField").assertIsDisplayed()
        Assert.assertTrue(composeTestRule.activity.lifecycle.currentState.isAtLeast(
                Lifecycle.State.RESUMED
            )
        )
    }

    @Test
    fun setLetters() {
        composeTestRule.onNodeWithTag("Continue").performClick()
        composeTestRule.onNodeWithTag("LettersField").performTextInput("letter")
        composeTestRule.onNodeWithTag("Ok").performClick()
        composeTestRule.onNodeWithTag("LettersField").assertDoesNotExist()
        composeTestRule.onNodeWithTag("letters").assertTextContains("letter")
    }
}