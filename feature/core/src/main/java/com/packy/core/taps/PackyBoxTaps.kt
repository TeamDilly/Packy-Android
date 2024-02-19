package com.packy.core.taps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme

@Composable
fun PackyBoxTap(
    modifier: Modifier = Modifier,
    text: @Composable () -> String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val background = if (isSelected) PackyTheme.color.gray900 else PackyTheme.color.white
    val textColor = if (isSelected) PackyTheme.color.white else PackyTheme.color.gray900
    Box(
        modifier = modifier
            .background(
                color = background,
                shape = CircleShape
            )
            .padding(
                vertical = 6.dp,
                horizontal = 16.dp
            )
            .clickableWithoutRipple(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text(),
            style = PackyTheme.typography.body03,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(2.dp)
        )
    }

}

@Preview
@Composable
fun BoxTapPreview() {
    PackyTheme {
        PackyBoxTap(
            text = { "선택됩" },
            isSelected = true
        ) {

        }
    }
}

@Preview
@Composable
fun BoxTapUnSelectedPreview() {
    PackyTheme {
        PackyBoxTap(
            text = { "선택안됨" },
            isSelected = false
        ) {

        }
    }
}