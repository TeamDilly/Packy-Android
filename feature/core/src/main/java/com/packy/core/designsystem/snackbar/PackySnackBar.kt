package com.packy.core.designsystem.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.packy.core.theme.PackyTheme

val PackySnackBarHost: @Composable (SnackbarHostState) -> Unit =
    { snackBarHostState ->
        SnackbarHost(
            hostState = snackBarHostState
        ) { snackBarData ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(16.dp)
                    .background(
                        color = PackyTheme.color.black.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = snackBarData.visuals.message,
                    style = PackyTheme.typography.body04,
                    color = PackyTheme.color.white
                )
            }
        }
    }