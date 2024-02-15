package com.packy.createbox.createboax.addgift

import android.net.Uri
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxAddGiftIntent : MviIntent {
    data object OnCloseClick : CreateBoxAddGiftIntent
    data object OnResetGiftClick : CreateBoxAddGiftIntent
    data object OnSaveClick : CreateBoxAddGiftIntent
    data object OnCancelImageClick : CreateBoxAddGiftIntent

    data class ChangeImageUri(
        val imageUri: Uri?,
    ) : CreateBoxAddGiftIntent
}

data class CreateBoxAddGiftState(
    val imageUri: Uri?,
    val previousImageUri: Uri?,
    val changed: Boolean = false
) : UiState{
    val isSavable get() = imageUri != null
}

sealed interface CreateBoxAddGiftEffect : SideEffect {
    data class CloseBottomSheet(
        val changed: Boolean
    ) : CreateBoxAddGiftEffect

    data class SavePhotoItem(
        val imageUri: Uri?,
    ) : CreateBoxAddGiftEffect
}