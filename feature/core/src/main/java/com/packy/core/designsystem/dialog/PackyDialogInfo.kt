package com.packy.core.designsystem.dialog

import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName

@Stable
data class PackyDialogInfo(
    val title: String,
    val subTitle: String? = null,
    val dismiss: String,
    val confirm: String,
    val onConfirm: () -> Unit,
    val onDismiss: () -> Unit,
    val backHandler: (() -> Unit)? = null
)
