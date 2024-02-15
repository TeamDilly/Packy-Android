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
        imageUri = null,
        previousImageUri = null
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxAddGiftIntent.OnResetGiftClick> {
            sendEffect(
                CreateBoxAddGiftEffect.SavePhotoItem(
                    imageUri = null,
                )
            )
        }
        subscribeIntent<CreateBoxAddGiftIntent.OnCloseClick> {
            sendEffect(
                CreateBoxAddGiftEffect.CloseBottomSheet(
                    changed = currentState.changed
                )
            )
        }
        subscribeIntent<CreateBoxAddGiftIntent.OnSaveClick> {
            sendEffect(
                CreateBoxAddGiftEffect.SavePhotoItem(
                    imageUri = currentState.imageUri,
                )
            )
        }
        subscribeStateIntent<CreateBoxAddGiftIntent.OnCancelImageClick> { state, _ ->
            val changed = state.imageUri != state.previousImageUri
            state.copy(
                imageUri = null,
                changed = changed
            )
        }
        subscribeStateIntent<CreateBoxAddGiftIntent.ChangeImageUri> { state, intent ->
            val changed = intent.imageUri != state.previousImageUri
            if (intent.imageUri != null) {
                state.copy(
                    imageUri = intent.imageUri,
                    changed = changed
                )
            } else {
                state.copy(changed = changed)
            }
        }
    }

    fun initPhotoItem() {
        viewModelScope.launch {
            val createBox = createBoxUseCase.getCreatedBox()
            val photoUri = createBox.gift?.url?.let { Uri.parse(it) }
            setState {
                CreateBoxAddGiftState(
                    imageUri = photoUri,
                    previousImageUri = photoUri,
                )
            }
        }
    }
}