package com.packy.createbox.createboax.addsticker

import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.createbox.GetStickerUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxStickerViewModel @Inject constructor(
    private val getStickerUseCase: GetStickerUseCase
) :
    MviViewModel<CreateBoxStickerIntent, CreateBoxStickerState, CreateBoxStickerEffect>() {
    override fun createInitialState(): CreateBoxStickerState = CreateBoxStickerState(
        selectedSticker = null,
        stickerList = emptyList(),
        lastPage = false
    )

    override fun handleIntent() {

    }

    fun getSticker() {
        viewModelScope.launch {

            val stickerList = getStickerUseCase.getSticker(null)
                .filterSuccess()
                .unwrapResource()
                .single()

            setState(
                currentState.copy(
                    stickerList = stickerList.stickers,
                    lastPage = stickerList.last
                )
            )
        }
    }

    fun addSticker(id: Int){
        viewModelScope.launch {
            val stickerList = getStickerUseCase.getSticker(id)
                .filterSuccess()
                .unwrapResource()
                .single()

            val selectedSticker = stickerList.stickers + stickerList.stickers
            setState(
                currentState.copy(
                    stickerList = selectedSticker,
                    lastPage = stickerList.last
                )
            )

        }
    }
}