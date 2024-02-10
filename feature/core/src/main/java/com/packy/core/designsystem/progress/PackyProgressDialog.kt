package com.packy.core.designsystem.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.packy.core.theme.PackyTheme

@Composable
fun PackyProgressDialog() {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PackyTheme.color.gray600.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            PackyProgress()
        }
    }
}