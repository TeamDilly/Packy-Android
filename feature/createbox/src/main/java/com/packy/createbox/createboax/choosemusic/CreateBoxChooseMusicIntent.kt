package com.packy.createbox.createboax.choosemusic

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxChooseMusicIntent : MviIntent {
    data object OnCloseClick : CreateBoxChooseMusicIntent
    data object OnChooseYourMusicClick : CreateBoxChooseMusicIntent
    data object OnPackyMusicClick : CreateBoxChooseMusicIntent
}

data object CreateBoxChooseMusicState: UiState

sealed interface CreateBoxChooseMusicEffect: SideEffect{
    data object CloseBottomSheet : CreateBoxChooseMusicEffect
    data object MoveToYourMusic : CreateBoxChooseMusicEffect
    data object MoveToPackyMusic : CreateBoxChooseMusicEffect
}