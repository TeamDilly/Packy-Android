package com.example.giftbox.giftarr

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.giftbox.navigation.GiftBoxArgs
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            setState {
                GiftBoxArrState(
                    GiftBoxArgs.getGiftBoxArg(savedStateHandle)
                )
            }
        }
    }

    override fun handleIntent() {
        subscribeIntent<GiftBoxArrIntent.OnBackClick> { sendEffect(GiftBoxArrEffect.MoveToBack) }
        subscribeIntent<GiftBoxArrIntent.OnOpenClick> { sendEffect(GiftBoxArrEffect.MoveToOpenBox) }
    }
}