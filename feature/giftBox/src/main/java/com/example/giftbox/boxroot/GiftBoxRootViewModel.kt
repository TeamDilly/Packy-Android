package com.example.giftbox.boxroot

import androidx.lifecycle.viewModelScope
import com.packy.domain.model.box.BoxId
import com.packy.domain.usecase.box.GetBoxUseCase
import com.packy.lib.utils.Resource
import com.packy.lib.utils.filterLoading
import com.packy.lib.utils.filterSuccess
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftBoxRootViewModel @Inject constructor(
    private val getBoxUseCase: GetBoxUseCase
) :
    MviViewModel<GiftBoxRootIntent, GiftBoxRootState, GiftBoxRootEffect>() {
    override fun createInitialState(): GiftBoxRootState = GiftBoxRootState(
        giftBox = null,
    )

    override fun handleIntent() {}

    fun getGiftBox(boxId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getBoxUseCase.getBox(boxId)
                .filterLoading()
                .collect { boxResource ->
                    when (boxResource) {
                        is Resource.ApiError,
                        is Resource.NetworkError,
                        is Resource.NullResult -> sendEffect(GiftBoxRootEffect.FailToGetGIftBox)

                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            val giftBox = boxResource.data
                            currentState.copy(
                                giftBox = giftBox,
                            )
                            sendEffect(GiftBoxRootEffect.GetGiftBox)
                        }
                    }
                }
        }
    }

    companion object GetBoxErrorCode {
        const val GIFTBOX_NOT_FOUND = "GIFTBOX_NOT_FOUND"
        const val GIFTBOX_ALREADY_OPENDED = "GIFTBOX_ALREADY_OPENDED"
    }
}