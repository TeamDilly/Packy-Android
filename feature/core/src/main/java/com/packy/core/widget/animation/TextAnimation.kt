package com.packy.core.widget.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TextAnimation(
    animationDelay: Long = 200L,
): Pair<Float, Dp>{

    var animation by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        delay(animationDelay)
        animation = true
    }

    val transition = updateTransition(
        animation,
        label = "boxTransition"
    )

    val opacity by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = 1200
                0.3f at 0 with FastOutSlowInEasing
                1f at 400 with FastOutSlowInEasing
                1f at 750 with LinearOutSlowInEasing
                0f at 1200 with FastOutSlowInEasing
            }
        },
        label = "TextAnimation"
    ) { opacity ->
        if (opacity) 0f else 0.3f
    }

    val offsetY by transition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = 1200
                20.dp at 0 with FastOutSlowInEasing
                0.dp at 400 with FastOutSlowInEasing
                0.dp at 1200
            }
        },
        label = "TextAnimation"
    )
    { offsetY ->
        if (offsetY) 0.dp else 0.dp
    }

    return Pair(opacity, offsetY)
}