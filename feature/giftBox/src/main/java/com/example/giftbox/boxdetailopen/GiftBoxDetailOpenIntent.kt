package com.example.giftbox.boxdetailopen

import com.packy.core.widget.youtube.YoutubeState
import com.packy.domain.model.getbox.GiftBox
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface GiftBoxDetailOpenIntent : MviIntent {
    data object OnPhotoClick : GiftBoxDetailOpenIntent

    data object OnLetterClick : GiftBoxDetailOpenIntent

    data object OnGiftClick : GiftBoxDetailOpenIntent

    data object CloseDialog : GiftBoxDetailOpenIntent

    data object OnBackClick : GiftBoxDetailOpenIntent
    data object OnCloseClick : GiftBoxDetailOpenIntent

    data object BoxShared: GiftBoxDetailOpenIntent
}

enum class ShowDetail {
    NONE,
    PHOTO,
    LETTER,
    GIFT
}

data class GiftBoxDetailOpenState(
    val giftBox: GiftBox?,
    val youtubeState: YoutubeState = YoutubeState.INIT,
    val showDetail: ShowDetail = ShowDetail.NONE,
    val shouldShowShared: Boolean = false
) : UiState {
    val hasGift get() = giftBox?.gift != null
}

sealed interface GiftBoxDetailOpenEffect : SideEffect {

    data object MoveToBack : GiftBoxDetailOpenEffect
    data object GiftBoxClose : GiftBoxDetailOpenEffect

    data class MoveToShared(val giftBoxId: Long): GiftBoxDetailOpenEffect
}