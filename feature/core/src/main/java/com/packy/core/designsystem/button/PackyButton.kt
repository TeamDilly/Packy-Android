package com.packy.core.designsystem.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun PackyButton(
    modifier: Modifier = Modifier,
    style: PackyButtonStyle,
    enabled: Boolean = true,
    text: String? = null,
    @DrawableRes icon: Int? = null,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = when {
        !enabled -> style.buttonColor.disabled
        isPressed -> style.buttonColor.pressed
        !isPressed -> style.buttonColor.default
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
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(style.buttonSize.radius))
            .height(style.buttonSize.height)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Button Icon",
                    tint = contentColor,
                    modifier = Modifier.size(style.buttonSize.iconSize)
                )
            }
            if (icon != null && text != null) {
                Spacer(modifier = Modifier.width(style.buttonSize.contentPadding))
            }
            if (text != null) {
                Text(text = text, style = style.buttonSize.textStyle, color = contentColor)
            }
        }
    }
}