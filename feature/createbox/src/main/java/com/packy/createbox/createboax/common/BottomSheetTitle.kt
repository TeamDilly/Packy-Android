package com.packy.createbox.createboax.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.packy.core.common.Spacer
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings

@Composable
fun ColumnScope.BottomSheetTitle(bottomSheetTitle: BottomSheetTitleContent) {
    Text(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        text = bottomSheetTitle.title,
        style = PackyTheme.typography.heading01,
        color = PackyTheme.color.gray900
    )
    Spacer(height = 4.dp)
    Text(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        text = bottomSheetTitle.description,
        style = PackyTheme.typography.body04,
        color = PackyTheme.color.gray600
    )
    Spacer(1f)
}

@Stable
data class BottomSheetTitleContent(
    val title: String,
    val description: String
)