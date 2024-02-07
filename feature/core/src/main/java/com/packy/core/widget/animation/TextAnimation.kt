package com.packy.core.widget.animation

import androidx.compose.animation.core.updateTransition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TextAnimation(
    text: @Composable () -> String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier
) {
    val transition = updateTransition(
        Unit,
        label = "boxTransition"
    )


    Text(
        text = text(),
        style = style,
        color = color,
        modifier = modifier
    )
}