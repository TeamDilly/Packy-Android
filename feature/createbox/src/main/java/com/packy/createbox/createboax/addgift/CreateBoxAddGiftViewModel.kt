package com.packy.createbox.createboax.addgift

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxAddGiftViewModel @Inject constructor() :
    MviViewModel<CreateBoxAddGiftIntent, CreateBoxAddGiftState, CreateBoxAddGiftEffect>() {
    override fun createInitialState(): CreateBoxAddGiftState = CreateBoxAddGiftState(
        imageUri = null
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxAddGiftIntent.OnCloseClick> {
            sendEffect(CreateBoxAddGiftEffect.CloseBottomSheet)
        }
        subscribeIntent<CreateBoxAddGiftIntent.OnSaveClick> {
            sendEffect(CreateBoxAddGiftEffect.SavePhotoItem(imageUri = currentState.imageUri))
        }
        subscribeStateIntent<CreateBoxAddGiftIntent.OnCancelImageClick> { state, _ ->
            state.copy(imageUri = null)
        }
        subscribeStateIntent<CreateBoxAddGiftIntent.ChangeImageUri> { state, intent ->
            state.copy(imageUri = intent.imageUri)
        }
    }
}