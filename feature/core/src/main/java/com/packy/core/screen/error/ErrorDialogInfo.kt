package com.packy.core.screen.error

data class ErrorDialogInfo(
    val message: String? = null,
    val backHandler: (() -> Unit)? = null,
    val retry: () -> Unit,
)
