package com.packy.core.common

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource?,
    onClick: () -> Unit
) = composed({
    value = interactionSource
}
) {
    val nonNullInteractionSource = interactionSource ?: remember {
        MutableInteractionSource()
    }
    clickable(
        indication = null,
        interactionSource = nonNullInteractionSource,
        onClick = onClick
    )
}