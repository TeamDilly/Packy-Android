package com.packy.createbox.createboax.addphoto

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxAddPhotoIntent: MviIntent {
    data object OnCloseClick : CreateBoxAddPhotoIntent
    data object OnSaveClick : CreateBoxAddPhotoIntent
}

data class ImageItem(
    val imageUri: String,
    val contentDescription: String
)

data class CreateBoxAddPhotoState(
    val imageList: List<ImageItem>,
    val isSavable: Boolean
): UiState

sealed interface CreateBoxAddPhotoEffect: SideEffect{
    data object CloseBottomSheet : CreateBoxAddPhotoEffect
}