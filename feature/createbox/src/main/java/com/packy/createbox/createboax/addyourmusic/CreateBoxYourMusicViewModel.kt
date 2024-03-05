package com.packy.createbox.createboax.addyourmusic

import com.packy.domain.usecase.youtube.ValidationYoutubeUrlUseCase
import com.packy.lib.ext.validationYoutubeVideoId
import com.packy.lib.utils.errorMessageHandler
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import javax.inject.Inject

@HiltViewModel
class CreateBoxYourMusicViewModel @Inject constructor(
    private val validationYoutubeUrlUseCase: ValidationYoutubeUrlUseCase
) :
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
            state.copy(
                youtubeLink = intent.newLink,
                validationYoutubeLink = null
            )
        }
        subscribeIntent<CreateBoxYourMusicIntent.OnSaveClick>(saveYoutubeMusic())
        subscribeIntent<CreateBoxYourMusicIntent.OnValidateCheckYoutubeLink> { _ ->
            validationYoutubeUrlUseCase.validationYoutubeUrl(currentState.youtubeLink)
                .errorMessageHandler {
                    setState {
                        it.copy(validationYoutubeLink = false)
                    }
                }
                .filterSuccess()
                .unwrapResource()
                .take(1)
                .collect{ value ->
                    setState {
                        it.copy(validationYoutubeLink = value )
                    }
                }
        }
        subscribeStateIntent<CreateBoxYourMusicIntent.OnYoutubeCancelClick> { state, _ ->
            state.copy(
                youtubeLink = "",
                validationYoutubeLink = null,
            )
        }
    }

    private fun saveYoutubeMusic(): suspend (CreateBoxYourMusicIntent.OnSaveClick) -> Unit =
        {
            val youtubeUri = currentState.youtubeLink
            if (currentState.validationYoutubeLink == true) {
                sendEffect(CreateBoxYourMusicEffect.SaveMusic(youtubeUri))
            }
        }
}