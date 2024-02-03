package com.example.giftbox.boxdetailopen

import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.domain.model.getbox.Envelope
import com.packy.domain.model.getbox.Gift
import com.packy.domain.model.getbox.GiftBox
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface GiftBoxDetailOpenIntent : MviIntent {
    data class OnPhotoClick(
        val photoUrl: String
    ) : GiftBoxDetailOpenIntent

    data class OnLetterClick(
        val envelope: Envelope,
        val letterContent: String,
    ) : GiftBoxDetailOpenIntent

    data class OnGiftClick(
        val gift: Gift
    ) : GiftBoxDetailOpenIntent

    data object OnBackClick : GiftBoxDetailOpenIntent
    data object OnCloseClick : GiftBoxDetailOpenIntent
}

data class GiftBoxDetailOpenState(
    val giftBox: GiftBox?
) : UiState

sealed interface GiftBoxDetailOpenEffect : SideEffect {
    data class ShowPhoto(
        val photoUrl: String
    ) : GiftBoxDetailOpenEffect

    data class ShowLetter(
        val envelope: Envelope,
        val letterContent: String,
    ) : GiftBoxDetailOpenEffect

    data class ShowGift(
        val gift: Gift
    ) : GiftBoxDetailOpenEffect

    data object MoveToBack : GiftBoxDetailOpenEffect
    data object GiftBoxClose : GiftBoxDetailOpenEffect
}