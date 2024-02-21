package com.example.home.mybox

import androidx.paging.PagingData
import com.packy.core.values.Strings
import com.packy.domain.model.home.HomeBox
import com.packy.domain.model.home.LazyBox
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface MyBoxIntent : MviIntent {
    data object OnBackClick : MyBoxIntent
    data class ChangeShowBoxType(val boxType: MyBoxType) : MyBoxIntent
    data class ClickMyBox(val boxId: Long, val shouldShowShared: Boolean) : MyBoxIntent

    data class OnMyBoxMoreClick(val boxId: Long) : MyBoxIntent
    data class OnLayBoxMoreClick(val boxId: Long) : MyBoxIntent
    data class OnClickDeleteMyBoxBottomSheet(
        val boxId: Long,
        val isLazyBox: Boolean = false
    ) : MyBoxIntent

    data class OnDeleteBoxClick(
        val boxId: Long,
        val isLazyBox: Boolean = false
    ) : MyBoxIntent
}

enum class MyBoxType(val title: String) {
    SEND(Strings.HOME_MY_BOX_SEND),
    RECEIVE(Strings.HOME_MY_BOX_RECEIVE)
}

data class MyBoxState(
    val showTab: MyBoxType,
    val sendBox: PagingData<HomeBox>,
    val receiveBox: PagingData<HomeBox>,
    val isLoading: Boolean = false,
    val removeItemBox: Set<Long> = emptySet(),
    val lazyBox: List<LazyBox> = emptyList()
) : UiState

sealed interface MyBoxEffect : SideEffect {
    data object MoveToBack : MyBoxEffect
    data class MoveToBoxDetail(val boxId: Long, val shouldShowShared: Boolean) : MyBoxEffect

    data class ShowDeleteBottomSheet(
        val boxId: Long,
        val isLazyBox: Boolean = false
    ) : MyBoxEffect

    data class ShowDeleteDialog(
        val boxId: Long,
        val isLazyBox: Boolean = false
    ) : MyBoxEffect
}