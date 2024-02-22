package com.example.home.home

import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.home.GetHomeBoxUseCase
import com.packy.domain.usecase.home.GetLazyBoxUseCase
import com.packy.domain.usecase.reset.ResetCreateBoxUseCase
import com.packy.lib.utils.Resource
import com.packy.lib.utils.errorMessageHandler
import com.packy.lib.utils.filterLoading
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.loadingHandler
import com.packy.lib.utils.plus
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeBoxUseCase: GetHomeBoxUseCase,
    private val getLazyBoxUseCase: GetLazyBoxUseCase,
    private val resetCreateBoxUseCase: ResetCreateBoxUseCase
) :
    MviViewModel<HomeIntent, HomeState, HomeEffect>() {
    override fun createInitialState(): HomeState = HomeState(
        giftBoxes = emptyList(),
        lazyBox = emptyList()
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
                .zip(getLazyBoxUseCase.getLazyBox()) { homeBox, lazyBox ->
                    homeBox + lazyBox
                }
                .loadingHandler { setState { state -> state.copy(isLoading = it) } }
                .errorMessageHandler { message ->
                    sendEffect(HomeEffect.ThrowError(message))
                }
                .filterSuccess()
                .unwrapResource()
                .collect { (homeBox, lazyBox) ->
                    setState { state ->
                        state.copy(
                            giftBoxes = homeBox,
                            lazyBox = lazyBox,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun resetPoint() {
        viewModelScope.launch(Dispatchers.IO) {
            resetCreateBoxUseCase.resetCreateBox()
        }
    }
}