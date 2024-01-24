package com.packy.createbox.boxaddinfo

import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface BoxAddInfoIntent : MviIntent {
    data object OnBackClick : BoxAddInfoIntent
    data object OnSaveButtonClick : BoxAddInfoIntent

    data class ChangeReceiver(
        val receiver: String
    ) : BoxAddInfoIntent

    data class ChangeSender(
        val sender: String
    ) : BoxAddInfoIntent
}

data class BoxAddInfoState(
    val letterSenderReceiver: LetterSenderReceiver
) : UiState {
    fun isSavable() =
        letterSenderReceiver.receiver.isNotEmpty() && letterSenderReceiver.sender.isNotEmpty()
}

sealed interface BoxAddInfoEffect : SideEffect {
    data object MoveToBack : BoxAddInfoEffect
    data object SaveBoxInfo : BoxAddInfoEffect
}