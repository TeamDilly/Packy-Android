package com.example.home.common.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DeleteBottomSheet(
    boxId: Long,
    sheetState: SheetState,
    scope: CoroutineScope = rememberCoroutineScope(),
    onDeleteClick: (Long) -> Unit = {},
    closeSheet: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = closeSheet,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = PackyTheme.color.white,
    ) {
        Column {
            Spacer(height = 8.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickableWithoutRipple {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                onDeleteClick(boxId)
                                if (!sheetState.isVisible) {
                                    closeSheet()
                                }
                            }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(

                    text = Strings.DELETE,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray900,
                    textAlign = TextAlign.Center
                )
            }

            Divider(
                color = PackyTheme.color.gray200,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickableWithoutRipple {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    closeSheet()
                                }
                            }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Strings.CANCEL,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray600,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(height = 16.dp)
        }
    }
}