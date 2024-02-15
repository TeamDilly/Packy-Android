package com.packy.createbox.createboax.addphoto

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.domain.usecase.photo.UploadImageUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateBoxAddPhotoViewModel @Inject constructor(
    private val getCreateBoxUseCase: CreateBoxUseCase
) :
    MviViewModel<CreateBoxAddPhotoIntent, CreateBoxAddPhotoState, CreateBoxAddPhotoEffect>() {
    override fun createInitialState(): CreateBoxAddPhotoState = CreateBoxAddPhotoState(
        photoItem = emptyImageItem,
        previousPhotoItem = emptyImageItem
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxAddPhotoIntent.OnCloseClick> {
            sendEffect(CreateBoxAddPhotoEffect.CloseBottomSheet(currentState.changed ?: false))
        }
        subscribeIntent<CreateBoxAddPhotoIntent.OnSaveClick> {
            sendEffect(
                CreateBoxAddPhotoEffect.SavePhotoItem(
                    currentState.photoItem
                )
            )
        }
        subscribeStateIntent<CreateBoxAddPhotoIntent.ChangeDescription> { state, intent ->
            state.copy(
                photoItem = state.photoItem.copy(contentDescription = intent.newDescription),
            )
        }
        subscribeStateIntent<CreateBoxAddPhotoIntent.ChangeImageUri> { state, intent ->
            state.copy(
                photoItem = state.photoItem.copy(imageUri = intent.imageUri),
            )
        }
        subscribeStateIntent<CreateBoxAddPhotoIntent.OnCancelImageClick> { state, _ ->
            state.copy(photoItem = emptyImageItem)
        }
    }

    fun initPhotoItem() {
        viewModelScope.launch {
            val createBox = getCreateBoxUseCase.getCreatedBox()
            val photoUri = createBox.photo?.photoUrl?.let { Uri.parse(it) }
            val newPhotoItem = PhotoItem(
                imageUri = photoUri,
                contentDescription = createBox.photo?.description
            )
            setState {
                it.copy(
                    photoItem = newPhotoItem,
                    previousPhotoItem = newPhotoItem
                )
            }
        }
    }

    companion object {
        val emptyImageItem = PhotoItem(
            null,
            null
        )
    }
}