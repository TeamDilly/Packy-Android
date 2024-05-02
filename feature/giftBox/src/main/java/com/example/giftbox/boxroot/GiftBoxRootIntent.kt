package com.example.giftbox.boxroot

import com.packy.domain.model.getbox.GiftBox
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface GiftBoxRootIntent : MviIntent {}

object GiftBoxRootState : UiState

sealed interface GiftBoxRootEffect : SideEffect {
    data class GetGiftBox(
        val giftBox: GiftBox
    ) : GiftBoxRootEffect

    data class FailToGetGIftBox(
        val giftBoxId: Long?
    ) : GiftBoxRootEffect
}