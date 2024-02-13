package com.example.home.archive

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor() :
    MviViewModel<ArchiveIntent, ArchiveState, ArchiveEffect>() {
    override fun createInitialState(): ArchiveState = ArchiveState(
        showArchiveType = ShowArchiveType.PHOTO
    )

    override fun handleIntent() {

    }
}