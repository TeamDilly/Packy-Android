package com.packy.createbox.createboax.addsticker

import com.packy.domain.model.createbox.Sticker
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxStickerIntent : MviIntent {}

data class CreateBoxStickerState(
    val selectedSticker: Sticker?,
    val stickerList: List<Sticker>
) : UiState

sealed interface CreateBoxStickerEffect : SideEffect {}