package com.packy.core.widget.dotted

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.packy.core.common.Spacer
import com.packy.core.theme.PackyTheme

@Composable
fun DottedText(
    modifier: Modifier = Modifier,
    text: @Composable () -> String,
    style: TextStyle,
    color: Color,
    contentSpacer: Dp = 16.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(
                    color = PackyTheme.color.gray800,
                    shape = CircleShape
                )
        )
        Spacer(contentSpacer)
        Text(
            text = text(),
            style = style,
            color = color
        )
    }
}