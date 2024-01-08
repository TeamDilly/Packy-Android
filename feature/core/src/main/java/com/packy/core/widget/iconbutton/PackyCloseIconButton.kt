package com.packy.core.widget.iconbutton

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.packy.core.R

@Composable
fun PackyCloseIconButton(
    modifier: Modifier = Modifier,
    style: PackyCloseIconButtonStype,
    enabled: Boolean = true,
    onClick: () -> Unit,
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
            .background(backgroundColor, shape = CircleShape)
            .size(style.buttonSize.size)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Icon(
            modifier = Modifier
                .size(style.buttonSize.iconSize)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.cancle),
            contentDescription = "Close Icon Button",
            tint = contentColor,
        )
    }
}