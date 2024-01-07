package com.packy.core.widget.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PackyButton(
    modifier: Modifier = Modifier,
    style: PackyButtonStyle,
    enabled: Boolean,
    onClick: () -> Unit,
    content: @Composable (Color) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = when {
        !enabled -> style.buttonColor.disabled
        isPressed -> style.buttonColor.default
        !isPressed -> style.buttonColor.pressed
        else -> style.buttonColor.disabled
    }

    val contentColor = when {
        !enabled -> style.buttonColor.disabledContentColor
        isPressed -> style.buttonColor.defaultContentColor
        !isPressed -> style.buttonColor.pressedContentColor
        else -> style.buttonColor.disabledContentColor
    }

    Box(
        modifier = modifier
            .height(style.buttonSize.height)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
            .border(
                width = 0.dp,
                color = backgroundColor,
                shape = RoundedCornerShape(style.buttonSize.radius),
            )
            .background(backgroundColor)
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content(contentColor)
        }
    }
}