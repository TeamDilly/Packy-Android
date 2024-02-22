package com.packy.core.widget.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun FlagChangeAnimation(
    flag: Boolean,
    modifier: Modifier = Modifier,
    enterAnimation: EnterTransition = fadeIn(
        tween(
            300,
            110
        )
    ),
    exitAnimation: ExitTransition = fadeOut(tween(110)),
    flagOnContent: @Composable () -> Unit,
    flagOffContent: @Composable () -> Unit,
) {
    ValueChangeAnimation(
        modifier = modifier,
        value = flag,
        enterAnimation = enterAnimation,
        exitAnimation = exitAnimation
    ) {
        if (it) {
            flagOnContent()
        } else {
            flagOffContent()
        }
    }
}