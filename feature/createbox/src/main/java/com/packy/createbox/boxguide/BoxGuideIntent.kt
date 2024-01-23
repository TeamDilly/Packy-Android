package com.packy.createbox.boxguide

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxGuideIntent : MviIntent {
    data object OnSaveClick : BoxGuideIntent
    data object OnBackClick : BoxGuideIntent
    data object OnChangedBoxClick : BoxGuideIntent
    data class ShowBottomSheet(
        val boxGuideBottomSheetRoute: BoxGuideBottomSheetRoute
    ) : BoxGuideIntent

    data class ChangeBoxContent(
        val boxGuideState: BoxGuideState
    ) : BoxGuideIntent
}

data class Photo(
    val photoUrl: String,
    val description: String
)

data class Envelope(
    val envelopeId: Int,
    val envelopeUrl: String
)

data class Latter(
    val latterContent: String,
    val envelope: Envelope
)

data class Sticker(
    val stickerId: Int,
    val stickerUri: String,
)

data class BoxGuideState(
    val photo: Photo?,
    val latter: Latter?,
    val youtubeUrl: String?,
    val sticker1: Sticker?,
    val sticker2: Sticker?,
) : UiState {
    fun isBoxComplete() =
        this.photo != null && this.latter != null && this.youtubeUrl != null && sticker1 != null && this.sticker2 != null
}

sealed interface BoxGuideEffect : SideEffect {
    data object MoveToBack : BoxGuideEffect
    data object SaveBoxInfo : BoxGuideEffect
    data object OnChangedBox : BoxGuideEffect
    data class ShowBottomSheet(
        val boxGuideBottomSheetRoute: BoxGuideBottomSheetRoute
    ) : BoxGuideEffect
}