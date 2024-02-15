package com.packy.createbox.createboax.addsticker

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.Sticker
import com.packy.domain.usecase.createbox.GetStickerUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxStickerViewModel @Inject constructor(
    private val getStickerUseCase: GetStickerUseCase
) :
    MviViewModel<CreateBoxStickerIntent, CreateBoxStickerState, CreateBoxStickerEffect>() {
    override fun createInitialState(): CreateBoxStickerState = CreateBoxStickerState(
        currentIndex = 0,
        selectedSticker = null,
        stickerList = PagingData.empty(),
    )

    override fun handleIntent() {
        subscribeStateIntent<CreateBoxStickerIntent.OnStickerClick> { state, intent ->
            val selectedSticker = state.selectedSticker
            val newSticker = intent.sticker
            val newSelectedSticker = if (selectedSticker?.isSelected(newSticker) == true) {
                val selectedLocation = selectedSticker.isSelectedNumber(newSticker)
                state.selectedSticker.set(
                    selectedLocation ?: currentState.currentIndex,
                    null
                )
                if (selectedLocation != currentState.currentIndex) {
                    state.selectedSticker.set(
                        state.currentIndex,
                        newSticker
                    )
                } else {
                    state.selectedSticker
                }
            } else {
                state.selectedSticker?.set(
                    state.currentIndex,
                    newSticker
                )
            }
            sendEffect(
                CreateBoxStickerEffect.OnChangeSticker(
                    selectedSticker = currentState.selectedSticker
                )
            )
            state.copy(selectedSticker = newSelectedSticker)
        }

        subscribeIntent<CreateBoxStickerIntent.OnSaveClick> {
            sendEffect(
                CreateBoxStickerEffect.OnSaveSticker(
                    selectedSticker = currentState.selectedSticker
                )
            )
        }
    }

    fun initState(
        currentIndex: Int,
        selectedSticker: SelectedSticker
    ) {
        setState(
            currentState.copy(
                currentIndex = currentIndex,
                selectedSticker = selectedSticker,
            )
        )
    }

    fun getSticker(
        selectedSticker: SelectedSticker,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getStickerUseCase.getSticker(selectedSticker)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    setState(
                        currentState.copy(
                            stickerList = it,
                        )
                    )
                }
        }
    }

    fun isStickerSelected(sticker: Sticker?): Int? {
        return currentState.selectedSticker?.isSelectedNumber(sticker)
    }
}