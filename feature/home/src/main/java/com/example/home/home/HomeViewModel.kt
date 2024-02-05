package com.example.home.home

import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.home.GetHomeBoxUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeBoxUseCase: GetHomeBoxUseCase
) :
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

    fun getGiftBoxse() {
        viewModelScope.launch(Dispatchers.IO) {
            getHomeBoxUseCase.getHomeBox()
                .filterSuccess()
                .collect {
                    setState { HomeState(it.giftBoxes) }
                }
        }
    }
}