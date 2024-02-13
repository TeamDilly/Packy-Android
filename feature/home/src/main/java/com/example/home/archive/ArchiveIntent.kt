package com.example.home.archive

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface ArchiveIntent : MviIntent {}

enum class ShowArchiveType{
    PHOTO,
    LETTER,
    MUSIC,
    GIFT
}

data class ArchiveState(
    val showArchiveType: ShowArchiveType
) : UiState

sealed interface ArchiveEffect : SideEffect {}