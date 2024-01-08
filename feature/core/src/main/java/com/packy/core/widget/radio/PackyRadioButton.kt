package com.packy.core.widget.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.packy.core.theme.PackyTheme

@Composable
fun PackyRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: (() -> Unit)?
) {
    if (selected) {
        Box(
            modifier = modifier
                .background(PackyTheme.color.gray900, shape = CircleShape)
                .size(20.dp)
                .clickable {
                    onClick?.invoke()
                },
        ) {
            Box(
                modifier = Modifier
                    .background(PackyTheme.color.white, shape = CircleShape)
                    .size(8.dp)
                    .align(Alignment.Center)
            )
        }
    } else {
        Box(
            modifier = modifier
                .background(PackyTheme.color.gray400, shape = CircleShape)
                .size(20.dp)
                .clickable {
                    onClick?.invoke()
                },
        ) {
            Box(
                modifier = Modifier
                    .background(PackyTheme.color.white, shape = CircleShape)
                    .size(15.dp)
                    .align(Alignment.Center)
            )
        }
    }
}