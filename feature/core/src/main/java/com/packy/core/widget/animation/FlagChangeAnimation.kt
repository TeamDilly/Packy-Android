package com.packy.core.widget.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable

@Composable
fun FlagChangeAnimation(
    flag: Boolean,
    flagOnContent: @Composable () -> Unit,
    flagOffContent: @Composable () -> Unit,
    enter: EnterTransition = fadeIn(),
    exit: ExitTransition = fadeOut(),
) {
    AnimatedVisibility(
        visible = flag,
        enter = enter,
        exit = exit
    ) {
        flagOnContent()
    }
    AnimatedVisibility(
        visible = !flag,
        enter = enter,
        exit = exit
    ) {
        flagOffContent()
    }
}