package com.packy.createbox.createboax.addyourmusic

import com.packy.core.widget.youtube.validationYoutubeVideoId
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxYourMusicViewModel @Inject constructor() :
    MviViewModel<CreateBoxYourMusicIntent, CreateBoxYourMusicState, CreateBoxYourMusicEffect>() {
    override fun createInitialState(): CreateBoxYourMusicState = CreateBoxYourMusicState(
        youtubeLink = "",
        validationYoutubeLink = null,
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxYourMusicIntent.OnBackClick> {
            sendEffect(CreateBoxYourMusicEffect.MoveToBack)
        }
        subscribeIntent<CreateBoxYourMusicIntent.OnCloseClick> {
            sendEffect(CreateBoxYourMusicEffect.CloseBottomSheet)
        }
        subscribeStateIntent<CreateBoxYourMusicIntent.OnYoutubeLinkChange> { state, intent ->
            state.copy(youtubeLink = intent.newLink, validationYoutubeLink = null)
        }
        subscribeIntent<CreateBoxYourMusicIntent.OnSaveClick> {
            sendEffect(CreateBoxYourMusicEffect.SaveMusic)
        }
        subscribeStateIntent<CreateBoxYourMusicIntent.OnValidateCheckYoutubeLink> { state, _ ->
            val validationLink = state.youtubeLink.validationYoutubeVideoId()
            state.copy(
                validationYoutubeLink = validationLink,
            )
        }
        subscribeStateIntent<CreateBoxYourMusicIntent.OnYoutubeCancelClick> { state, _ ->
            state.copy(
                youtubeLink = "",
                validationYoutubeLink = null,
            )
        }
    }
}