package com.packy.createbox.createboax.boxchange

import com.packy.domain.model.box.BoxDesign
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxChangeIntent : MviIntent {
    data object OnConfirmClick : CreateBoxChangeIntent
    data class ChangeBox(val box: BoxDesign) : CreateBoxChangeIntent
}

data class CreateBoxChangeState(
    val currentBox: BoxDesign?
) : UiState

sealed interface CreateBoxChangeEffect : SideEffect {
    data object OnConfirmClick: CreateBoxChangeEffect
}