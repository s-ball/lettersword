/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class HtmlScheme (
    val link: Color = lightHtmlScheme.link
)

val lightHtmlScheme = HtmlScheme(link = Color.Blue)
val darkHtmlScheme = HtmlScheme(link = Color(0xff87cefa))

val LocalHtmlScheme = compositionLocalOf { HtmlScheme() }