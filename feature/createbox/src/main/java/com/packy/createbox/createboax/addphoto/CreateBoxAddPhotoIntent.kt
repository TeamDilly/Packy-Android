package com.packy.createbox.createboax.addphoto

import android.net.Uri
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxAddPhotoIntent: MviIntent {
    data object OnCloseClick : CreateBoxAddPhotoIntent
    data object OnSaveClick : CreateBoxAddPhotoIntent
    data object OnCancelImageClick : CreateBoxAddPhotoIntent

    data class ChangeDescription(
        val newDescription: String
    ): CreateBoxAddPhotoIntent

    data class ChangeImageUri(
        val imageUri: Uri?
    ): CreateBoxAddPhotoIntent
}

data class ImageItem(
    val imageUri: Uri?,
    val contentDescription: String?
)

data class CreateBoxAddPhotoState(
    val imageItem: ImageItem,
    val isSavable: Boolean
): UiState

sealed interface CreateBoxAddPhotoEffect: SideEffect{
    data object CloseBottomSheet : CreateBoxAddPhotoEffect
}