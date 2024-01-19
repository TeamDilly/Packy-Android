package com.packy.createbox.createboax.addphoto

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxAddPhotoViewModel @Inject constructor(

) : MviViewModel<CreateBoxAddPhotoIntent, CreateBoxAddPhotoState, CreateBoxAddPhotoEffect>() {
    override fun createInitialState(): CreateBoxAddPhotoState = CreateBoxAddPhotoState(
        imageList = emptyList(),
        isSavable = false
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxAddPhotoIntent.OnCloseClick> {
            sendEffect(CreateBoxAddPhotoEffect.CloseBottomSheet)
        }
    }
}