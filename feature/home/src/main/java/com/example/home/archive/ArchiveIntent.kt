package com.example.home.archive

import com.packy.core.values.Strings
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface ArchiveIntent : MviIntent {
    data class OnArchiveTypeClick(
        val type: ShowArchiveType
    ): ArchiveIntent
}

enum class ShowArchiveType(val title: String){
    PHOTO(Strings.ARCHIVE_TAP_PHOTO),
    LETTER(Strings.ARCHIVE_TAP_LETTER),
    MUSIC(Strings.ARCHIVE_TAP_MUSIC),
    GIFT(Strings.ARCHIVE_TAP_GIFT)
}

data class ArchiveState(
    val showArchiveType: ShowArchiveType
) : UiState

sealed interface ArchiveEffect : SideEffect {}