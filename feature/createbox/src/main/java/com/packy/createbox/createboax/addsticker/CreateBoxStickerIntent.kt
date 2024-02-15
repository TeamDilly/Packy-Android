package com.packy.createbox.createboax.addsticker

import androidx.paging.PagingData
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.Sticker
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxStickerIntent : MviIntent {
    data object OnSaveClick : CreateBoxStickerIntent
    data class OnStickerClick(val sticker: Sticker?): CreateBoxStickerIntent
}

data class CreateBoxStickerState(
    val currentIndex: Int,
    val selectedSticker: SelectedSticker?,
    val stickerList: PagingData<Sticker>,
) : UiState

sealed interface CreateBoxStickerEffect : SideEffect {
    data class OnSaveSticker( val selectedSticker: SelectedSticker?): CreateBoxStickerEffect
    data class OnChangeSticker( val selectedSticker: SelectedSticker?) : CreateBoxStickerEffect
}