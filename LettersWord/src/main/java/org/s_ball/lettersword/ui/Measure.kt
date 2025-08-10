package org.s_ball.lettersword.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MeasuredText(txt: String, cr: (Dp) -> Unit) {
    val measurer = rememberTextMeasurer()
    val cont = measurer.measure(txt)
    val density = LocalDensity.current
    val dp = with(density) { cont.size.width.toDp() }
    /* val resul = "Size of $txt (WxH): ${cont.size.width}x${cont.size.height}\n" +
            " Width: ${with(density) { cont.size.width.toDp() }}dp - ${with(density) { cont.size.width.toSp() }}sp"
    Text(resul)*/
    cr(dp)
}

@Composable
fun Measures(mask: String, resul: (Dp) -> Unit) {
    val l = mask.length
    val withM = "m".repeat(l)
    var dp: Dp = 0.dp
    MeasuredText(withM) { dp = it }
    resul(dp)
}