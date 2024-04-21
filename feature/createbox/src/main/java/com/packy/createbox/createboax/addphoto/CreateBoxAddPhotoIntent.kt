package com.packy.createbox.createboax.addphoto

import android.net.Uri
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxAddPhotoIntent : MviIntent {
    data object OnCloseClick : CreateBoxAddPhotoIntent
    data object OnSaveClick : CreateBoxAddPhotoIntent
    data object OnCancelImageClick : CreateBoxAddPhotoIntent

    data class ChangeDescription(
        val newDescription: String
    ) : CreateBoxAddPhotoIntent

    data class ChangeImageUri(
        val imageUri: Uri?
    ) : CreateBoxAddPhotoIntent
}

data class PhotoItem(
    val imageUri: Uri?,
    val contentDescription: String = ""
)

data class CreateBoxAddPhotoState(
    val photoItem: PhotoItem,
    val previousPhotoItem: PhotoItem,
) : UiState {
    val changed: Boolean get() = photoItem != previousPhotoItem
    val isSavable: Boolean = photoItem.imageUri != null
}

sealed interface CreateBoxAddPhotoEffect : SideEffect {
    data class CloseBottomSheet(
        val changed: Boolean
    ) : CreateBoxAddPhotoEffect

    data class SavePhotoItem(
        val photoItem: PhotoItem
    ) : CreateBoxAddPhotoEffect
}