package com.packy.createbox.boxguide.widget

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
            .clickableWithoutRipple(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (content != null) {
            Box(modifier = Modifier.rotate(inclination)) {
                content()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(1.dp)
                    .rotate(inclination)
                    .drawBehind {
                        drawRoundRect(
                            color = Color.White.copy(alpha = 0.3f),
                            style = dottedStroke,
                            cornerRadius = CornerRadius(8.dp.toPx())
                        )
                    }
            )
            placeholder()
        }
    }
}
