package com.packy.core.taps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Text(
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
        text = text(),
        style = PackyTheme.typography.body03,
        color = textColor
    )
}

@Preview
@Composable
fun BoxTapPreview(){
    PackyTheme {
        PackyBoxTap(
            text = {"선택됩"},
            isSelected = true
        ) {

        }
    }
}

@Preview
@Composable
fun BoxTapUnSelectedPreview(){
    PackyTheme {
        PackyBoxTap(
            text = {"선택안됨"},
            isSelected = false
        ) {

        }
    }
}