package com.example.giftbox.giftarr

import androidx.lifecycle.SavedStateHandle
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GiftBoxArrViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) :
    MviViewModel<GiftBoxArrIntent, GiftBoxArrState, GiftBoxArrEffect>() {

    override fun createInitialState(): GiftBoxArrState = GiftBoxArrState(
        null
    )

    init {
        setState(
            GiftBoxArrState(
                GiftBoxRoute.getGiftBoxArg(savedStateHandle)
            )
        )
    }

    override fun handleIntent() {
        subscribeIntent<GiftBoxArrIntent.OnBackClick> { sendEffect(GiftBoxArrEffect.MoveToBack) }
        subscribeIntent<GiftBoxArrIntent.OnOpenClick> { sendEffect(GiftBoxArrEffect.MoveToOpenBox) }
    }
}