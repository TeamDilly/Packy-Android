package com.packy.createbox.createboax.addphoto

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxAddPhotoViewModel @Inject constructor() :
    MviViewModel<CreateBoxAddPhotoIntent, CreateBoxAddPhotoState, CreateBoxAddPhotoEffect>() {
    override fun createInitialState(): CreateBoxAddPhotoState = CreateBoxAddPhotoState(
        emptyImageItem,
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxAddPhotoIntent.OnCloseClick> {
            sendEffect(CreateBoxAddPhotoEffect.CloseBottomSheet)
        }
        subscribeIntent<CreateBoxAddPhotoIntent.OnSaveClick> {
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
        const val MAX_ONBOARDING_PAGE_SIZE = 2
    }
}