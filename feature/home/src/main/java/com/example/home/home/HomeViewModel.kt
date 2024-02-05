package com.example.home.home

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() :
    MviViewModel<HomeIntent, HomeState, HomeEffect>() {
    override fun createInitialState(): HomeState = HomeState(
        giftBoxes = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<HomeIntent.OnBoxDetailClick> { sendEffect(HomeEffect.MoveToBoxDetail(it.boxId)) }
        subscribeIntent<HomeIntent.OnCrateBoxClick> { sendEffect(HomeEffect.MoveToCreateBox) }
        subscribeIntent<HomeIntent.OnSettingClick> { sendEffect(HomeEffect.MoveToSetting) }
        subscribeIntent<HomeIntent.OnMoreBoxClick> { sendEffect(HomeEffect.MoveToMoreBox) }
    }
}