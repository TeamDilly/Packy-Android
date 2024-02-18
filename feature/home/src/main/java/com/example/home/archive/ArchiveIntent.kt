package com.example.home.archive

import androidx.paging.PagingData
import com.packy.core.values.Strings
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.model.archive.ArchiveMusic
import com.packy.domain.model.archive.ArchivePhoto
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
    val showArchiveType: ShowArchiveType,
    val photos: PagingData<ArchivePhoto>,
    val musics: PagingData<ArchiveMusic>,
    val letter: PagingData<ArchiveLetter>,
    val gifts: PagingData<ArchiveGift>,
    val isLoading: Boolean = false,
) : UiState

sealed interface ArchiveEffect : SideEffect {}