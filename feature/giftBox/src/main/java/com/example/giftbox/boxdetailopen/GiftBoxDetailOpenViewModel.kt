package com.example.giftbox.boxdetailopen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.giftbox.giftarr.GiftBoxArrState
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftBoxDetailOpenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) :
    MviViewModel<GiftBoxDetailOpenIntent, GiftBoxDetailOpenState, GiftBoxDetailOpenEffect>() {
    override fun createInitialState(): GiftBoxDetailOpenState = GiftBoxDetailOpenState(
        giftBox = null
    )

    init {
        setState(
            GiftBoxDetailOpenState(
                GiftBoxRoute.getGiftBoxArg(savedStateHandle)
            )
        )
        savedStateHandle.get<Boolean>(GiftBoxRoute.GIFT_BOX_SHOULD_SHOW_SHARED)?.let { shouldShow ->
            viewModelScope.launch {
                setState {
                    it.copy(
                        shouldShowShared = shouldShow
                    )
                }
            }
        }
    }

    override fun handleIntent() {
        subscribeStateIntent<GiftBoxDetailOpenIntent.OnPhotoClick>(showPhoto())
        subscribeStateIntent<GiftBoxDetailOpenIntent.OnLetterClick>(showLetter())
        subscribeStateIntent<GiftBoxDetailOpenIntent.OnGiftClick>(showGift())
        subscribeStateIntent<GiftBoxDetailOpenIntent.CloseDialog> { state, _ ->
            state.copy(
                showDetail = ShowDetail.NONE
            )
        }
        subscribeIntent<GiftBoxDetailOpenIntent.OnBackClick> { sendEffect(GiftBoxDetailOpenEffect.MoveToBack) }
        subscribeIntent<GiftBoxDetailOpenIntent.OnCloseClick> { sendEffect(GiftBoxDetailOpenEffect.GiftBoxClose) }
    }

    private fun showGift(): suspend (GiftBoxDetailOpenState, GiftBoxDetailOpenIntent.OnGiftClick) -> GiftBoxDetailOpenState =
        { state, _ ->
            val gift = currentState.giftBox?.gift
            state.copy(
                showDetail = if (gift != null) ShowDetail.GIFT else ShowDetail.NONE
            )
        }

    private fun showLetter(): suspend (GiftBoxDetailOpenState, GiftBoxDetailOpenIntent.OnLetterClick) -> GiftBoxDetailOpenState =
        { state, _ ->
            val envelope = currentState.giftBox?.envelope
            val letterContent = currentState.giftBox?.letterContent
            if (envelope != null && letterContent != null)
                state.copy(
                    showDetail = ShowDetail.LETTER
                )
            else state
        }

    private fun showPhoto(): suspend (GiftBoxDetailOpenState, GiftBoxDetailOpenIntent.OnPhotoClick) -> GiftBoxDetailOpenState =
        { state, _ ->
            val photo = currentState.giftBox?.photos?.firstOrNull()
            if (photo != null) state.copy(
                showDetail = ShowDetail.PHOTO
            )
            else state
        }
}