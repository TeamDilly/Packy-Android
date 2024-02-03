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
        subscribeIntent<GiftBoxDetailOpenIntent.OnPhotoClick> {sendEffect(GiftBoxDetailOpenEffect.ShowPhoto(it.photoUrl))  }
        subscribeIntent<GiftBoxDetailOpenIntent.OnLetterClick> {sendEffect(GiftBoxDetailOpenEffect.ShowLetter(it.envelope, it.letterContent))  }
        subscribeIntent<GiftBoxDetailOpenIntent.OnGiftClick> {sendEffect(GiftBoxDetailOpenEffect.ShowGift(it.gift))  }
        subscribeIntent<GiftBoxDetailOpenIntent.OnBackClick> {sendEffect(GiftBoxDetailOpenEffect.MoveToBack)  }
        subscribeIntent<GiftBoxDetailOpenIntent.OnCloseClick> {sendEffect(GiftBoxDetailOpenEffect.GiftBoxClose)  }
    }
}