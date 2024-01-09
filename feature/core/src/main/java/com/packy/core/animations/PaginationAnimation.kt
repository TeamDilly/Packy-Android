package com.packy.core.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import java.time.Duration

object PaginationAnimation {
    fun slidInTop(
        durationMillis: Int = 300
    ): EnterTransition {
        return slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = durationMillis, easing = LinearOutSlowInEasing)
        )
    }

    fun slidOutToBottom(
        durationMillis: Int = 300
    ): ExitTransition {
        return slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = durationMillis, easing = LinearOutSlowInEasing)
        )
    }

    fun slidInToStart(
        durationMillis: Int = 300
    ): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = durationMillis, easing = LinearOutSlowInEasing)
        )
    }

    fun slidOutToEnd(
        durationMillis: Int = 300
    ): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = durationMillis, easing = LinearOutSlowInEasing)
        )
    }
}