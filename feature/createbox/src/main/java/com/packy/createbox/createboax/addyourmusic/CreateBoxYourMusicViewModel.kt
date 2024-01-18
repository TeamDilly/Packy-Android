package com.packy.createbox.createboax.addyourmusic

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxYourMusicViewModel @Inject constructor() :
    MviViewModel<CreateBoxYourMusicIntent, CreateBoxYourMusicState, CreateBoxYourMusicEffect>() {
    override fun createInitialState(): CreateBoxYourMusicState = CreateBoxYourMusicState(
        youtubeLink = "",
        validationYoutubeLink = null
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxYourMusicIntent.OnBackClick> {
            sendEffect(CreateBoxYourMusicEffect.MoveToBack)
        }
        subscribeIntent<CreateBoxYourMusicIntent.OnCloseClick> {
            sendEffect(CreateBoxYourMusicEffect.CloseBottomSheet)
        }
        subscribeStateIntent<CreateBoxYourMusicIntent.OnYoutubeLinkChange> { state, intent ->
            state.copy(youtubeLink = intent.newLink)
        }
        subscribeIntent<CreateBoxYourMusicIntent.OnSaveClick> {

        }
        subscribeIntent<CreateBoxYourMusicIntent.OnValidateCheckYoutubeLink> {

        }
    }
}