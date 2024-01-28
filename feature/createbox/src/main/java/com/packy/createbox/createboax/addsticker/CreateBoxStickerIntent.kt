package com.packy.createbox.createboax.addsticker

import com.packy.domain.model.createbox.Sticker
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxStickerIntent : MviIntent {
    data object OnSaveClick: CreateBoxStickerIntent
}

data class SelectedSticker(
    val selectedSticker1 : Sticker?,
    val selectedSticker2 : Sticker?,
)

data class CreateBoxStickerState(
    val selectedSticker: SelectedSticker?,
    val stickerList: List<Sticker>,
    val lastPage: Boolean
) : UiState

sealed interface CreateBoxStickerEffect : SideEffect {
    data class OnSaveSticker(val sticker: Sticker): SideEffect
}