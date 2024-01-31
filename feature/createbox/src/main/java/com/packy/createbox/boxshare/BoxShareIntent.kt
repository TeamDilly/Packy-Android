package com.packy.createbox.boxshare

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxShareIntent : MviIntent {
    data object ShareKakao : BoxShareIntent
}

data class BoxShareState(
    val boxImageUrl: String?
) : UiState

sealed interface BoxShareEffect : SideEffect {
    data object SuccessShare : BoxShareEffect
    data object FailedShare : BoxShareEffect
}