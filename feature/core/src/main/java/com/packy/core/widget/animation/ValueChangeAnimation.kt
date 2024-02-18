package com.packy.core.widget.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun <T> ValueChangeAnimation(
    value: T,
    modifier: Modifier = Modifier,
    enterAnimation: EnterTransition = fadeIn(),
    exitAnimation: ExitTransition = fadeOut(),
    content: @Composable (T) -> Unit,
) {
    var animatedValue by remember { mutableStateOf(value) }

    LaunchedEffect(key1 = value) {
        animatedValue = value
    }

    AnimatedContent(
        modifier = modifier,
        targetState = animatedValue,
        transitionSpec = {
            enterAnimation.togetherWith(exitAnimation)
        },
        content = { content(it) },
        label = "ValueChangeAnimation"
    )
}