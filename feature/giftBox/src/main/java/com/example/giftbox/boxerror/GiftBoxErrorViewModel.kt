package com.example.giftbox.boxerror

import androidx.lifecycle.SavedStateHandle
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GiftBoxErrorViewModel @Inject constructor(
) :
    MviViewModel<GiftBoxErrorIntent, GiftBoxErrorState, GiftBoxErrorEffect>() {
    override fun createInitialState(): GiftBoxErrorState = GiftBoxErrorState(
        message = ""
    )

    override fun handleIntent() {

    }
}