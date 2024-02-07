package com.example.home.home

import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.home.GetHomeBoxUseCase
import com.packy.domain.usecase.reset.ResetCreateBoxUseCase
import com.packy.lib.utils.Resource
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeBoxUseCase: GetHomeBoxUseCase,
    private val resetCreateBoxUseCase: ResetCreateBoxUseCase
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

    fun getGiftBoxes() {
        viewModelScope.launch(Dispatchers.IO) {
            getHomeBoxUseCase.getHomeBox()
                .filterSuccess()
                .unwrapResource()
                .collect { homeBox ->
                    setState { state -> state.copy(giftBoxes = homeBox) }
                }
        }
    }

    fun resetPoint(){
        viewModelScope.launch(Dispatchers.IO) {
            resetCreateBoxUseCase.resetCreateBox()
        }
    }
}