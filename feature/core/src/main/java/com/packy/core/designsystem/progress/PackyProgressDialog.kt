package com.packy.core.designsystem.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.packy.core.theme.PackyTheme
import kotlinx.coroutines.delay

@Composable
fun PackyProgressDialog(
    loadingDelay: Long = 1000L
) {
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(loadingDelay)
        isLoading = true
    }
    if (isLoading) {
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
}