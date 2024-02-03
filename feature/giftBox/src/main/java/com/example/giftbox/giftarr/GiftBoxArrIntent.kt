package com.example.giftbox.giftarr

import com.packy.domain.model.getbox.GiftBox
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface GiftBoxArrIntent : MviIntent {
    data object OnBackClick: GiftBoxArrIntent
    data object OnOpenClick: GiftBoxArrIntent
}

data class GiftBoxArrState(
    val giftBox: GiftBox?
) : UiState

sealed interface GiftBoxArrEffect : SideEffect {
    data object MoveToBack: GiftBoxArrEffect
    data object MoveToOpenBox: GiftBoxArrEffect
}