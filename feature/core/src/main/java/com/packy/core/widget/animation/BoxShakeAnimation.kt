package com.packy.core.widget.animation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import kotlinx.coroutines.delay

@Composable
fun BoxShakeAnimation(
    modifier: Modifier = Modifier,
    animationPlay: Boolean = true,
    content: @Composable () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "BoxShakeAnimation")
    val shake by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
                0f at 0 with FastOutLinearInEasing
                1.3f at 100 with FastOutLinearInEasing
                -1.3f at 200 with FastOutLinearInEasing
                1.3f at 300 with FastOutLinearInEasing
                -1.3f at 400 with FastOutLinearInEasing
                1.3f at 500 with FastOutLinearInEasing
                -1.3f at 600 with FastOutLinearInEasing
                0.7f at 700 with FastOutLinearInEasing
                0f at 800 with FastOutLinearInEasing
                0f at 1500
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "BoxShakeAnimation"
    )

    if (animationPlay) {
        Box(modifier = modifier.rotate(shake)) {
            content()
        }
    } else {
        Box(modifier = modifier) {
            content()
        }
    }
}