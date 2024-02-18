package com.example.home.archive

import com.packy.domain.usecase.archive.GetArchiveGift
import com.packy.domain.usecase.archive.GetArchiveLetter
import com.packy.domain.usecase.archive.GetArchiveMusic
import com.packy.domain.usecase.archive.GetArchivePhoto
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    val getArchiveMusic: GetArchiveMusic,
    val getArchivePhoto: GetArchivePhoto,
    val getArchiveGift: GetArchiveGift,
    val getArchiveLetter: GetArchiveLetter
) :
    MviViewModel<ArchiveIntent, ArchiveState, ArchiveEffect>() {
    override fun createInitialState(): ArchiveState = ArchiveState(
        showArchiveType = ShowArchiveType.PHOTO
    )

    override fun handleIntent() {
        subscribeStateIntent<ArchiveIntent.OnArchiveTypeClick> { state, intent ->
            state.copy(showArchiveType = intent.type)
        }
    }
}