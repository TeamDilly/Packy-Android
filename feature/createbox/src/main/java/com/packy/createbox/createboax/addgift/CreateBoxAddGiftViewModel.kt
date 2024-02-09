package com.packy.createbox.createboax.addgift

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxAddGiftViewModel @Inject constructor(
    private val createBoxUseCase: CreateBoxUseCase
) :
    MviViewModel<CreateBoxAddGiftIntent, CreateBoxAddGiftState, CreateBoxAddGiftEffect>() {
    override fun createInitialState(): CreateBoxAddGiftState = CreateBoxAddGiftState(
        imageUri = null
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxAddGiftIntent.OnCloseClick> {
            setState(currentState.copy(imageUri = null))
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

    fun initPhotoItem() {
        viewModelScope.launch {
            val createBox = createBoxUseCase.getCreatedBox()
            val photoUri = createBox.gift?.url?.let { Uri.parse(it) }
            setState {
                CreateBoxAddGiftState(
                    imageUri = photoUri
                )
            }
        }
    }
}