package com.packy.createbox.boxguide.widget

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.widget.animation.FlagChangeAnimation
import com.packy.core.widget.animation.ValueChangeAnimation
import com.packy.core.widget.dotted.dottedStroke

@Composable
fun BoxGuideContent(
    modifier: Modifier = Modifier,
    inclination: Float = 0f,
    onClick: () -> Unit = {},
    placeholder: @Composable () -> Unit,
    content: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickableWithoutRipple(onClick = onClick)
            .rotate(inclination),
        contentAlignment = Alignment.Center
    ) {
        FlagChangeAnimation(
            flag = content != null,
            enterAnimation = fadeIn(
                animationSpec = tween(
                    400,
                    delayMillis = 120
                )
            ) + scaleIn(
                initialScale = 1.3f,
                animationSpec = tween(
                    400,
                    delayMillis = 120
                )
            ),
            flagOnContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (content != null) {
                        content()
                    }
                }
            },
            flagOffContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(1.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = Color.White.copy(alpha = 0.3f),
                                style = dottedStroke,
                                cornerRadius = CornerRadius(8.dp.toPx())
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    placeholder()
                }
            }
        )
    }
}
