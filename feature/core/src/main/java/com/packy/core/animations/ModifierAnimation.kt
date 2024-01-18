package com.packy.core.animations

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.absoluteValue

fun Modifier.infiniteRotationAnimation(
    paused: Boolean = false,
    durationMillis: Int = 2000,
    easing: Easing = LinearEasing
): Modifier = composed {

    val infiniteTransition = rememberInfiniteTransition(label = "cdPlay Animation")
    var currentValue by remember {
        mutableFloatStateOf(0f)
    }
    val rotation by infiniteTransition.animateFloat(
        initialValue = currentValue,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = easing),
            repeatMode = RepeatMode.Restart
        ), label = "cdPlay Animation"
    )

    return@composed this.then(
        Modifier.graphicsLayer(
            rotationZ = rotation
        ).also {
            if (paused) {
                currentValue = rotation
            }
        }
    )
}