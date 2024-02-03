package com.example.giftbox.boxerror

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface GiftBoxErrorIntent : MviIntent {}

data class GiftBoxErrorState(
    val message: String
) : UiState

sealed interface GiftBoxErrorEffect : SideEffect {}