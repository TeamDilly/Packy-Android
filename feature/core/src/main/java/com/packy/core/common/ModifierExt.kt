package com.packy.core.common

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.packy.core.theme.PackyTheme

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) = composed({
    value = interactionSource
}
) {
    val nonNullInteractionSource = interactionSource ?: remember {
        MutableInteractionSource()
    }
    clickable(
        enabled = enabled,
        indication = null,
        interactionSource = nonNullInteractionSource,
        onClick = onClick
    )
}

@Composable
fun Modifier.conditionalBorder(
    condition: Boolean,
    width: Dp = 1.dp,
    shape: Shape = RectangleShape,
    color: @Composable () -> Color = { PackyTheme.color.black }
): Modifier = Modifier.then(
    if (condition) {
        this.border(
            width = width,
            color = color(),
            shape = shape
        )
    } else {
        Modifier
    }
)