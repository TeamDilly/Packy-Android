package com.packy.createbox.boxshare

import com.packy.common.kakaoshare.KakaoCustomFeed
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxShareIntent : MviIntent {
    data object ShareKakao : BoxShareIntent
    data object OnExitClick : BoxShareIntent

    data object OnLazySharClick : BoxShareIntent

    data object OnLaySharedTextClick : BoxShareIntent

    data object LazyShared : BoxShareIntent
}

data class BoxShareState(
    val boxImageUrl: String?,
    val boxTitle: String?,
    val receiverName: String?,
    val shared: Boolean?,
    val createdBox: String? = null,
    val isLoading: Boolean = false,
) : UiState

sealed interface BoxShareEffect : SideEffect {

    data class KakaoShare(
        val kakaoCustomFeed: KakaoCustomFeed
    ) : BoxShareEffect

    data object MoveToBack : BoxShareEffect
    data object MoveToMain : BoxShareEffect

    data object ShowLazyShareDialog : BoxShareEffect
}