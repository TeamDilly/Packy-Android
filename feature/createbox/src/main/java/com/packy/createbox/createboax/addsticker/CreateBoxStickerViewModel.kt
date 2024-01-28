package com.packy.createbox.createboax.addsticker

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.packy.domain.usecase.createbox.GetStickerUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
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
        stickerList = PagingData.empty(),
    )

    override fun handleIntent() {

    }

    fun getSticker() {
        viewModelScope.launch {
            getStickerUseCase.getSticker()
                .distinctUntilChanged()
                .collect {
                    setState(
                        currentState.copy(
                            stickerList = it,
                        )
                    )
                }
        }
    }
}