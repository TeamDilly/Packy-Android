package com.example.home.home

import androidx.lifecycle.viewModelScope
import com.packy.domain.model.home.NoticeGiftBox
import com.packy.domain.usecase.box.DeleteBoxUseCase
import com.packy.domain.usecase.box.GetBoxUseCase
import com.packy.domain.usecase.home.GetHomeBoxUseCase
import com.packy.domain.usecase.home.GetLazyBoxUseCase
import com.packy.domain.usecase.home.GetNoticeGiftBoxUseCase
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeBoxUseCase: GetHomeBoxUseCase,
    private val getLazyBoxUseCase: GetLazyBoxUseCase,
    private val deleteBoxUseCase: DeleteBoxUseCase,
    private val resetCreateBoxUseCase: ResetCreateBoxUseCase,
    private val getNoticeGiftBoxUseCase: GetNoticeGiftBoxUseCase,
    private val getGiftBoxUseCase: GetBoxUseCase
) :
    MviViewModel<HomeIntent, HomeState, HomeEffect>() {
    override fun createInitialState(): HomeState = HomeState(
        giftBoxes = emptyList(),
        lazyBox = emptyList(),
        noticeGiftBox = null
    )

    override fun handleIntent() {
        subscribeIntent<HomeIntent.OnBoxDetailClick> {
            if (it.showMotion) {
                routeGiftBoxMotion(it)
            } else {
                sendEffect(
                    HomeEffect.MoveToBoxDetail(
                        it.giftBoxId,
                        false
                    )
                )
            }
        }
        subscribeIntent<HomeIntent.OnLazyBoxDetailClick> {
            sendEffect(
                HomeEffect.MoveToBoxDetail(
                    it.boxId,
                    true
                )
            )
        }
        subscribeIntent<HomeIntent.OnBottomSheetMoreClick> {
            sendEffect(HomeEffect.ShowBottomSheetMore(it.boxId))
        }
        subscribeIntent<HomeIntent.OnClickDeleteMyBoxBottomSheet> {
            sendEffect(HomeEffect.ShowDeleteDialog(it.boxId))
        }
        subscribeIntent<HomeIntent.OnDeleteBoxClick> {
            deleteBox(it.boxId)
        }
        subscribeIntent<HomeIntent.OnCrateBoxClick> { sendEffect(HomeEffect.MoveToCreateBox) }
        subscribeIntent<HomeIntent.OnSettingClick> { sendEffect(HomeEffect.MoveToSetting) }
        subscribeIntent<HomeIntent.OnMoreBoxClick> { sendEffect(HomeEffect.MoveToMoreBox) }
        subscribeStateIntent<HomeIntent.HideBottomSheet> { state, _ ->
            state.copy(noticeGiftBox = null)
        }
    }

    private suspend fun routeGiftBoxMotion(it: HomeIntent.OnBoxDetailClick) {
        getGiftBoxUseCase.getBox(it.giftBoxId)
            .loadingHandler { setState { state -> state.copy(isLoading = it) } }
            .filterSuccess()
            .unwrapResource()
            .collect { giftBox ->
                sendEffect(
                    HomeEffect.MoveToBoxOpenMotion(
                        giftBox
                    )
                )
            }
    }

    private fun deleteBox(giftBoxId: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteBoxUseCase.deleteBox(giftBoxId)
                .loadingHandler { setState { state -> state.copy(isLoading = it) } }
                .errorMessageHandler { message ->
                    sendEffect(HomeEffect.ThrowError(message))
                }
                .filterSuccess()
                .unwrapResource()
                .collect {
                    getGiftBoxes()
                }
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

    fun getNoticeGiftBox(){
        viewModelScope.launch(Dispatchers.IO) {
            getNoticeGiftBoxUseCase.getNoticeGiftBox()
                .loadingHandler { setState { state -> state.copy(isLoading = it) } }
                .filterSuccess()
                .unwrapResource()
                .collect { noticeGiftBox ->
                    setState { state ->
                        state.copy(
                            noticeGiftBox = noticeGiftBox,
                            isLoading = false
                        )
                    }
                }
        }
    }
}