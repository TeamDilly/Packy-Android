package com.packy.core.screen.error

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings

@Composable
fun ErrorDialog(
    info: ErrorDialogInfo,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = { info.backHandler?.invoke() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PackyTheme.color.white),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = Strings.ERROR_TITLE,
                style = PackyTheme.typography.heading02,
                color = PackyTheme.color.gray900
            )
            Text(
                text = info.message ?: Strings.ERROR_DESCRIPTION,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray600
            )
            Spacer(24.dp)
            Text(
                modifier = Modifier
                    .background(
                        color = PackyTheme.color.purple500,
                        shape = CircleShape
                    )
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    )
                    .clickableWithoutRipple {
                        info.retry()
                    },
                text = Strings.ERROR_RETRY,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.white
            )
        }
    }
}