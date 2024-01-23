package com.packy.createbox.boxguide

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxGuideIntent : MviIntent {}

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

sealed interface BoxGuideEffect : SideEffect {}