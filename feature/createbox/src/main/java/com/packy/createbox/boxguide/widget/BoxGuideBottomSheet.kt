package com.packy.createbox.boxguide.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme

@Composable
fun BoxGuideBottomSheet(
    visible: Boolean,
    changeVisible: () -> Unit,
    modifier: Modifier = Modifier,
    animationDuration: Int = 300,
    dimVisible: Boolean = true,
    content: @Composable () -> Unit
) {

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                animationSpec = tween(durationMillis = animationDuration)
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = animationDuration)
            )
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    color = PackyTheme.color.gray900.copy(
                        alpha = if (dimVisible) 0.6f else 0f
                    )
                )
                .clickableWithoutRipple {
                    changeVisible()
                })
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = animationDuration)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = animationDuration)
            )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(
                        color = PackyTheme.color.white,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
            ) {
                content()
            }
        }
    }
}