package com.packy.createbox.boxaddinfo

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxAddInfoIntent : MviIntent {
    data object OnBackClick : BoxAddInfoIntent
    data object OnSaveButtonClick : BoxAddInfoIntent

    data class ChangeToName(
        val toName: String
    ) : BoxAddInfoIntent

    data class ChangeFromName(
        val fromName: String
    ) : BoxAddInfoIntent
}

data class BoxAddInfoState(
    val toName: String,
    val fromName: String
) : UiState

sealed interface BoxAddInfoEffect : SideEffect {
    data object MoveToBack : BoxAddInfoEffect
    data object SaveBoxInfo : BoxAddInfoEffect
}