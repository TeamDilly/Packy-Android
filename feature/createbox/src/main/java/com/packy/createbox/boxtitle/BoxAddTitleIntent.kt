package com.packy.createbox.boxtitle

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxAddTitleIntent : MviIntent {
    data object OnBackClick : BoxAddTitleIntent
    data class MoveToNext(val boxTitle: String) : BoxAddTitleIntent

    data class OnTitleChange(val text: String) : BoxAddTitleIntent
}

data class BoxAddTitleState(
    val boxTitle: String,
    val boxAllReady: Boolean = false
) : UiState

sealed interface BoxAddTitleEffect : SideEffect {
    data object MoveToBack : BoxAddTitleEffect
    data class SaveBoxTitle(
        val boxTitle: String
    ) : BoxAddTitleEffect
}