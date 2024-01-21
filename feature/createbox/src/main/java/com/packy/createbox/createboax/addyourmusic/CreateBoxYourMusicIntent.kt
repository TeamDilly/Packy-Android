package com.packy.createbox.createboax.addyourmusic

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxYourMusicIntent : MviIntent {
    data object OnBackClick : CreateBoxYourMusicIntent
    data object OnCloseClick : CreateBoxYourMusicIntent
    data object OnSaveClick : CreateBoxYourMusicIntent
    data class OnYoutubeLinkChange(val newLink: String) : CreateBoxYourMusicIntent
    data object OnValidateCheckYoutubeLink : CreateBoxYourMusicIntent
    data object OnYoutubeCancelClick : CreateBoxYourMusicIntent
}

data class CreateBoxYourMusicState(
    val youtubeLink: String,
    val validationYoutubeLink: Boolean?,
) : UiState

sealed interface CreateBoxYourMusicEffect : SideEffect {
    data object CloseBottomSheet : CreateBoxYourMusicEffect
    data object MoveToBack : CreateBoxYourMusicEffect

    data object SaveMusic : CreateBoxYourMusicEffect
}