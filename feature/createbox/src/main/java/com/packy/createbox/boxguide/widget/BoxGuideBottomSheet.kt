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
    content: @Composable () -> Unit
) {
    val alphaValue by animateFloatAsState(
        targetValue = if (visible) 0.6f else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = LinearEasing
        ),
        label = ""
    )

    val density = LocalDensity.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = PackyTheme.color.gray900.copy(
                    alpha = alphaValue
                )
            )
            .clickableWithoutRipple {
                changeVisible()
            }
    ) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { with(density) { 300.dp.roundToPx() } },
                animationSpec = tween(durationMillis = animationDuration)
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { with(density) { 300.dp.roundToPx() } },
                animationSpec = tween(durationMillis = animationDuration)
            ) + fadeOut()
        ) {
            Surface(
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