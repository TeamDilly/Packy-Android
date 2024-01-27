package com.packy.createbox.createboax.addsticker

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxStickerViewModel @Inject constructor() :
    MviViewModel<CreateBoxStickerIntent, CreateBoxStickerState, CreateBoxStickerEffect>() {
    override fun createInitialState(): CreateBoxStickerState = CreateBoxStickerState(
        selectedSticker = null,
        stickerList = emptyList()
    )

    override fun handleIntent() {

    }
}