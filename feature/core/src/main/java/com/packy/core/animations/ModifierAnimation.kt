package com.packy.core.animations

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.infiniteRotationAnimation(
    play: Boolean = true,
    durationMillis: Int = 2000,
    easing: Easing = LinearEasing
): Modifier = composed {
    if (!play) return@composed this
    val infiniteTransition = rememberInfiniteTransition(label = "cdPlay Animation")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = easing),
            repeatMode = RepeatMode.Restart
        ), label = "cdPlay Animation"
    )

    return@composed this.then(
        Modifier.graphicsLayer(
            rotationZ = rotation
        )
    )
}