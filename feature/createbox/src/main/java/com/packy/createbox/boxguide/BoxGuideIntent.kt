package com.packy.createbox.boxguide

import android.net.Uri
import com.packy.core.widget.youtube.YoutubeState
import com.packy.domain.model.box.BoxDesign
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.Sticker
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxGuideIntent : MviIntent {
    data object OnSaveClick : BoxGuideIntent
    data object OnBackClick : BoxGuideIntent
    data class ShowBottomSheet(
        val boxGuideBottomSheetRoute: BoxGuideBottomSheetRoute
    ) : BoxGuideIntent

    data class SavePhoto(
        val imageUri: Uri,
        val contentDescription: String
    ) : BoxGuideIntent

    data class SaveLetter(
        val letter: Letter
    ) : BoxGuideIntent

    data class SaveMusic(
        val youtubeUrl: String
    ) : BoxGuideIntent

    data class SaveSticker(
        val index: Int,
        val sticker: Sticker?
    ) : BoxGuideIntent

    data class SaveGift(
        val imageUri: Uri?
    ) : BoxGuideIntent

    data class SaveBox(
        val boxDesign: BoxDesign
    ) : BoxGuideIntent

    data object ClearMusic : BoxGuideIntent

    data object OnTutorialClick: BoxGuideIntent
}

data class Photo(
    val photoUrl: Uri,
    val contentDescription: String
)

data class Envelope(
    val envelopeId: Int,
    val envelopeUrl: String,
)

data class Letter(
    val letterContent: String,
    val envelope: Envelope
)

data class BoxGuideState(
    val title: String,
    val photo: Photo?,
    val letter: Letter?,
    val youtubeUrl: String?,
    val youtubeState: YoutubeState = YoutubeState.INIT,
    val selectedSticker: SelectedSticker,
    val gift: Uri?,
    val boxDesign: BoxDesign?,
    val showTutorial: Boolean = false
) : UiState {
    fun isBoxComplete() =
        this.photo != null && this.letter != null && this.youtubeUrl != null && this.selectedSticker.isStickerComplete()
}

sealed interface BoxGuideEffect : SideEffect {
    data object MoveToBack : BoxGuideEffect
    data object SaveBox : BoxGuideEffect
    data class FailedSaveBox(val message: String) : BoxGuideEffect
    data class ShowBottomSheet(
        val boxGuideBottomSheetRoute: BoxGuideBottomSheetRoute
    ) : BoxGuideEffect
}