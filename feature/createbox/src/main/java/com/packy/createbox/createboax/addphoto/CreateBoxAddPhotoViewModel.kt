package com.packy.createbox.createboax.addphoto

import com.packy.core.widget.youtube.YoutubeState
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxAddPhotoViewModel @Inject constructor() :
    MviViewModel<CreateBoxAddPhotoIntent, CreateBoxAddPhotoState, CreateBoxAddPhotoEffect>() {
    override fun createInitialState(): CreateBoxAddPhotoState = CreateBoxAddPhotoState(
        emptyImageItem,
        isSavable = false
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxAddPhotoIntent.OnCloseClick> {
            sendEffect(CreateBoxAddPhotoEffect.CloseBottomSheet)
        }
        subscribeStateIntent<CreateBoxAddPhotoIntent.ChangeDescription> { state, intent ->
            state.copy(imageItem = state.imageItem.copy(contentDescription = intent.newDescription))
        }
        subscribeStateIntent<CreateBoxAddPhotoIntent.ChangeImageUri> { state, intent ->
            state.copy(imageItem = state.imageItem.copy(imageUri = intent.imageUri))
        }
        subscribeStateIntent<CreateBoxAddPhotoIntent.OnCancelImageClick> { state, _ ->
            state.copy(imageItem = emptyImageItem)
        }
    }

    companion object {
        val emptyImageItem = ImageItem(
            null,
            null
        )
        const val MAX_ONBOARDING_PAGE_SIZE = 2
    }
}