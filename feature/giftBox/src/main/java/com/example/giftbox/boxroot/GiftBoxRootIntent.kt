package com.example.giftbox.boxroot

import com.packy.data.model.getbox.GiftBox
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface GiftBoxRootIntent : MviIntent {}

data class GiftBoxRootState(
    val giftBox: GiftBox?,
) : UiState

sealed interface GiftBoxRootEffect : SideEffect {
    data object GetGiftBox: GiftBoxRootEffect
    data object FailToGetGIftBox: GiftBoxRootEffect
}