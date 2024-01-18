package com.packy.createbox.createboax.addpackymusic

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxPackyMusicViewModel @Inject constructor() :
    MviViewModel<CreateBoxPackyMusicIntent, CreateBoxPackyMusicState, CreateBoxPackyMusicEffect>() {
    override fun createInitialState(): CreateBoxPackyMusicState = CreateBoxPackyMusicState(
        currentTitle = "",
        currentHashTag = emptyList(),
        currentMusicIndex = 0,
        musicState = CreateBoxPackyMusicState.MusicState.PAUSE
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxPackyMusicIntent.OnBackClick> {
            sendEffect(CreateBoxPackyMusicEffect.MoveToBack)
        }
        subscribeIntent<CreateBoxPackyMusicIntent.OnCloseClick> {
            sendEffect(CreateBoxPackyMusicEffect.CloseBottomSheet)
        }
        subscribeIntent<CreateBoxPackyMusicIntent.OnSaveClick> {

        }

        subscribeStateIntent<CreateBoxPackyMusicIntent.ChangeMusicState> { state, _ ->
            state.copy(musicState = state.musicState.next())
        }
        subscribeStateIntent<CreateBoxPackyMusicIntent.ChangeMusic> { state, intent ->
            state.copy(currentMusicIndex = intent.index)
        }
    }
}