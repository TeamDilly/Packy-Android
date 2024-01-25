package com.packy.createbox.boxchoice

import com.packy.domain.model.box.BoxDesign
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxChoiceIntent : MviIntent {
    data object OnBackClick : BoxChoiceIntent
    data object OnCloseClick : BoxChoiceIntent

    data object OnSaveClick : BoxChoiceIntent

    data class ChangeSelectBox(val selectedBox: BoxDesign) : BoxChoiceIntent
}

data class BoxChoiceState(
    val selectedBox: BoxDesign?,
    val boxDesignList: List<BoxDesign>
) : UiState

sealed interface BoxChoiceEffect : SideEffect {
    data object MoveToBack : BoxChoiceEffect
    data object CloseCreateBox : BoxChoiceEffect

    data class SaveBoxInfo(
        val shouldShowBoxMotion: Boolean,
        val boxDesign: BoxDesign?
    ): BoxChoiceEffect
}