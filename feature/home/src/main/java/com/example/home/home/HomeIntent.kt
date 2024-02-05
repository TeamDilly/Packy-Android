package com.example.home.home

import com.packy.domain.model.home.HomeBox
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface HomeIntent : MviIntent {
    data object OnSettingClick : HomeIntent
    data object OnCrateBoxClick : HomeIntent

    data class OnBoxDetailClick(
        val boxId: Long
    ) : HomeIntent

    data object OnMoreBoxClick : HomeIntent
}

data class HomeState(
    val giftBoxes: List<HomeBox>
) : UiState

sealed interface HomeEffect : SideEffect {
    data object MoveToSetting : HomeEffect
    data object MoveToCreateBox : HomeEffect

    data class MoveToBoxDetail(val boxId: Long) : HomeEffect

    data object MoveToMoreBox: HomeEffect
}