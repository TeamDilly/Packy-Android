package com.example.giftbox.boxdetailopen

import androidx.lifecycle.SavedStateHandle
import com.example.giftbox.giftarr.GiftBoxArrState
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    }

    override fun handleIntent() {
        subscribeIntent<GiftBoxDetailOpenIntent.OnPhotoClick>(showPhoto())
        subscribeIntent<GiftBoxDetailOpenIntent.OnLetterClick>(showLetter())
        subscribeIntent<GiftBoxDetailOpenIntent.OnGiftClick>(showGift())
        subscribeIntent<GiftBoxDetailOpenIntent.OnBackClick> { sendEffect(GiftBoxDetailOpenEffect.MoveToBack) }
        subscribeIntent<GiftBoxDetailOpenIntent.OnCloseClick> { sendEffect(GiftBoxDetailOpenEffect.GiftBoxClose) }
    }

    private fun showGift(): suspend (GiftBoxDetailOpenIntent.OnGiftClick) -> Unit =
        {
            val gift = currentState.giftBox?.gift
            if(gift != null){
                sendEffect(GiftBoxDetailOpenEffect.ShowGift(gift))
            }
        }

    private fun showLetter(): suspend (GiftBoxDetailOpenIntent.OnLetterClick) -> Unit =
        {
            val envelope = currentState.giftBox?.envelope
            val letterContent = currentState.giftBox?.letterContent
            if(envelope != null && letterContent != null)
                sendEffect(
                    GiftBoxDetailOpenEffect.ShowLetter(
                        envelope,
                        letterContent
                    )
                )
        }

    private fun showPhoto(): suspend (GiftBoxDetailOpenIntent.OnPhotoClick) -> Unit =
        { sendEffect(GiftBoxDetailOpenEffect.ShowPhoto(currentState.giftBox?.photos?.firstOrNull()?.photoUrl)) }
}