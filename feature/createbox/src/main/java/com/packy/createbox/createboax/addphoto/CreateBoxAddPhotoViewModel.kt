package com.packy.createbox.createboax.addphoto

import com.packy.domain.usecase.photo.UploadImageUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateBoxAddPhotoViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase
) :
    MviViewModel<CreateBoxAddPhotoIntent, CreateBoxAddPhotoState, CreateBoxAddPhotoEffect>() {
    override fun createInitialState(): CreateBoxAddPhotoState = CreateBoxAddPhotoState(
        emptyImageItem,
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxAddPhotoIntent.OnCloseClick> {
            sendEffect(CreateBoxAddPhotoEffect.CloseBottomSheet)
        }
        subscribeIntent<CreateBoxAddPhotoIntent.OnSaveClick> {
            uploadImageUseCase.uploadImage(
                UUID.randomUUID().toString(),
                currentState.photoItem.imageUri.toString()
            )
            sendEffect(
                CreateBoxAddPhotoEffect.SavePhotoItem(
                    currentState.photoItem
                )
            )
        }
        subscribeStateIntent<CreateBoxAddPhotoIntent.ChangeDescription> { state, intent ->
            state.copy(photoItem = state.photoItem.copy(contentDescription = intent.newDescription))
        }
        subscribeStateIntent<CreateBoxAddPhotoIntent.ChangeImageUri> { state, intent ->
            state.copy(photoItem = state.photoItem.copy(imageUri = intent.imageUri))
        }
        subscribeStateIntent<CreateBoxAddPhotoIntent.OnCancelImageClick> { state, _ ->
            state.copy(photoItem = emptyImageItem)
        }
    }

    companion object {
        val emptyImageItem = PhotoItem(
            null,
            null
        )
    }
}