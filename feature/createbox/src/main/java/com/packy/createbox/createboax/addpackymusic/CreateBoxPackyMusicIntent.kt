package com.packy.createbox.createboax.addpackymusic

import com.packy.core.widget.youtube.YoutubeState
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxPackyMusicIntent : MviIntent {

    data object OnBackClick : CreateBoxPackyMusicIntent
    data object OnCloseClick : CreateBoxPackyMusicIntent
    data object OnSaveClick : CreateBoxPackyMusicIntent
    data class ChangeMusic(val index: Int) : CreateBoxPackyMusicIntent

    data class ChangeMusicState(val state: YoutubeState) : CreateBoxPackyMusicIntent
}

data class PackyMusic(
    val title: String,
    val hashTag: List<String>,
    val videoId: String,
    val thumbnail: String
)

data class CreateBoxPackyMusicState(
    val currentTitle: String,
    val currentHashTag: List<String>,
    val currentMusicIndex: Int,
    val musicState: YoutubeState
) : UiState

sealed interface CreateBoxPackyMusicEffect : SideEffect {
    data object CloseBottomSheet : CreateBoxPackyMusicEffect
    data object MoveToBack : CreateBoxPackyMusicEffect
    data object MoveToAddPhoto : CreateBoxPackyMusicEffect
}
