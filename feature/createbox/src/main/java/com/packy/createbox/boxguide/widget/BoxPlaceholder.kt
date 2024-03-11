package com.packy.createbox.boxguide.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.packy.core.common.Spacer
import com.packy.core.theme.PackyTheme

@Composable
fun BoxPlaceholder(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "box guide placeholder icon",
            tint = PackyTheme.color.white,
        )
        Spacer(height = 9.dp)
        Text(
            text = title,
            style = PackyTheme.typography.body04,
            color = PackyTheme.color.white
        )
    }
}