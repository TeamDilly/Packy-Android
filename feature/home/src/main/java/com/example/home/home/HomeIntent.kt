package com.example.home.home

import com.packy.domain.model.getbox.GiftBox
import com.packy.domain.model.home.HomeBox
import com.packy.domain.model.home.LazyBox
import com.packy.domain.model.home.NoticeGiftBox
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface HomeIntent : MviIntent {
    data object OnSettingClick : HomeIntent
    data object OnCrateBoxClick : HomeIntent

    data class OnBoxDetailClick(
        val giftBoxId: Long,
        val showMotion: Boolean = false
    ) : HomeIntent

    data class OnLazyBoxDetailClick(
        val boxId: Long
    ) : HomeIntent

    data class OnBottomSheetMoreClick(
        val boxId: Long
    ) : HomeIntent

    data class OnClickDeleteMyBoxBottomSheet(
        val boxId: Long
    ) : HomeIntent

    data class OnDeleteBoxClick(
        val boxId: Long
    ) : HomeIntent

    data object OnMoreBoxClick : HomeIntent

    data object HideBottomSheet: HomeIntent
}

data class HomeState(
    val giftBoxes: List<HomeBox>,
    val lazyBox: List<LazyBox>,
    val isLoading: Boolean = false,
    val noticeGiftBox: NoticeGiftBox? = null
) : UiState

sealed interface HomeEffect : SideEffect {
    data object MoveToSetting : HomeEffect
    data object MoveToCreateBox : HomeEffect

    data class MoveToBoxDetail(
        val boxId: Long,
        val isLazyBox: Boolean,
    ) : HomeEffect
    data class MoveToBoxOpenMotion(
        val giftBox: GiftBox,
    ) : HomeEffect

    data object MoveToMoreBox : HomeEffect
    data class ThrowError(val message: String?) : HomeEffect
    data class ShowBottomSheetMore(val boxId: Long) : HomeEffect
    data class ShowDeleteDialog(val boxId: Long) : HomeEffect
}