package com.packy.createbox.boxchoice

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxChoiceIntent : MviIntent {
    data object OnBackClick : BoxChoiceIntent
    data object OnCloseClick : BoxChoiceIntent

    data object OnSaveClick : BoxChoiceIntent

    data class ChangeSelectBox(val selectedBox: BoxDesign) : BoxChoiceIntent
}

data class BoxDesign(
    val id: Int,
    val boxTopUri: String,
    val boxBottomUri: String,
)

data class BoxChoiceState(
    val selectedBox: BoxDesign?,
    val boxDesignList: List<BoxDesign>
) : UiState

sealed interface BoxChoiceEffect : SideEffect {
    data object MoveToBack : BoxChoiceEffect
    data object CloseCreateBox : BoxChoiceEffect

    data object SaveBoxInfo : BoxChoiceEffect
}