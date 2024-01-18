package com.packy.createbox.createboax.addpackymusic

import com.packy.createbox.createboax.addyourmusic.CreateBoxYourMusicIntent
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxPackyMusicIntent : MviIntent {

    data object OnBackClick : CreateBoxPackyMusicIntent
    data object OnCloseClick : CreateBoxPackyMusicIntent
    data object OnSaveClick : CreateBoxPackyMusicIntent
    data class ChangeMusic(val index: Int) : CreateBoxPackyMusicIntent

    data object ChangeMusicState : CreateBoxPackyMusicIntent
}

data class CreateBoxPackyMusicState(
    val currentTitle: String,
    val currentHashTag: List<String>,
    val currentMusicIndex: Int,
    val musicState: MusicState
) : UiState {
    enum class MusicState {
        PLAY,
        PAUSE;

        fun next(): MusicState{
            return when(this){
                PLAY -> PAUSE
                PAUSE -> PLAY
            }
        }
    }
}

sealed interface CreateBoxPackyMusicEffect : SideEffect {
    data object CloseBottomSheet : CreateBoxPackyMusicEffect
    data object MoveToBack : CreateBoxPackyMusicEffect
    data object MoveToAddPhoto : CreateBoxPackyMusicEffect
}
