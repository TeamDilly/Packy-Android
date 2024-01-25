package com.packy.createbox.boxguide

import android.net.Uri
import com.packy.core.widget.youtube.YoutubeState
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
        val Letter: Letter
    ) : BoxGuideIntent

    data class SaveMusic(
        val youtubeUrl: String
    ) : BoxGuideIntent

    data object ClearMusic: BoxGuideIntent
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
    val LetterContent: String,
    val envelope: Envelope
)

data class Sticker(
    val stickerId: Int,
    val stickerUri: String,
)

data class BoxGuideState(
    val title: String,
    val photo: Photo?,
    val Letter: Letter?,
    val youtubeUrl: String?,
    val youtubeState: YoutubeState = YoutubeState.INIT,
    val sticker1: Sticker?,
    val sticker2: Sticker?,
) : UiState {
    fun isBoxComplete() =
        this.photo != null && this.Letter != null && this.youtubeUrl != null && sticker1 != null && this.sticker2 != null
}

sealed interface BoxGuideEffect : SideEffect {
    data object MoveToBack : BoxGuideEffect
    data object SaveBox : BoxGuideEffect
    data object OnChangedBox : BoxGuideEffect
    data class ShowBottomSheet(
        val boxGuideBottomSheetRoute: BoxGuideBottomSheetRoute
    ) : BoxGuideEffect
}