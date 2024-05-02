package com.example.home.mybox

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.google.firebase.analytics.FirebaseAnalytics
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.AnalyticsEvent
import com.packy.core.analytics.FirebaseAnalyticsWrapper
import com.packy.core.analytics.toBundle
import com.packy.domain.usecase.box.DeleteBoxUseCase
import com.packy.domain.usecase.home.GetHomeBoxPaginationUseCase
import com.packy.domain.usecase.home.GetLazyBoxUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.loadingHandler
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBoxViewModel @Inject constructor(
    private val getHomeBoxPaginationUseCase: GetHomeBoxPaginationUseCase,
    private val deleteBoxUseCase: DeleteBoxUseCase,
    private val getLazyBoxUseCase: GetLazyBoxUseCase
) :
    MviViewModel<MyBoxIntent, MyBoxState, MyBoxEffect>() {

    override fun createInitialState(): MyBoxState = MyBoxState(
        showTab = MyBoxType.SEND,
        sendBox = PagingData.empty(),
        receiveBox = PagingData.empty()
    )

    override fun handleIntent() {
        subscribeIntent<MyBoxIntent.OnBackClick> { sendEffect(MyBoxEffect.MoveToBack) }
        subscribeIntent<MyBoxIntent.ClickMyBox> {
            sendEffect(
                MyBoxEffect.MoveToBoxDetail(
                    it.boxId,
                    it.shouldShowShared
                )
            )
        }
        subscribeIntent<MyBoxIntent.OnMyBoxMoreClick> { sendEffect(MyBoxEffect.ShowDeleteBottomSheet(it.boxId)) }
        subscribeIntent<MyBoxIntent.OnClickDeleteMyBoxBottomSheet> {
            sendEffect(
                MyBoxEffect.ShowDeleteDialog(
                    it.boxId,
                    it.isLazyBox
                )
            )
        }
        subscribeIntent<MyBoxIntent.OnLayBoxMoreClick> {
            sendEffect(
                MyBoxEffect.ShowDeleteBottomSheet(
                    boxId = it.boxId,
                    isLazyBox = true
                )
            )
        }
        subscribeIntent<MyBoxIntent.OnDeleteBoxClick> { intent ->
            deleteBoxUseCase.deleteBox(intent.boxId)
                .loadingHandler { setState { state -> state.copy(isLoading = it) } }
                .filterSuccess()
                .collect {
                    setState { state ->
                        if (intent.isLazyBox) {
                            state.copy(
                                lazyBox = state.lazyBox.filter { it.boxId != intent.boxId }
                            )
                        } else {
                            state.copy(
                                removeItemBox = state.removeItemBox + intent.boxId
                            )
                        }
                    }
                }
        }
        subscribeStateIntent<MyBoxIntent.ChangeShowBoxType> { state, intent ->
            if (state.showTab == intent.boxType) {
                return@subscribeStateIntent state
            }
            FirebaseAnalyticsWrapper.logEvent(
                label = AnalyticsConstant.AnalyticsLabel.VIEW,
                bundle = arrayOf<AnalyticsEvent>(
                    AnalyticsConstant.PageName.MY_BOX,
                    if (intent.boxType == MyBoxType.SEND) AnalyticsConstant.ComponentName.SEND
                    else AnalyticsConstant.ComponentName.RECEIVE
                ).toBundle()
            )
            state.copy(showTab = intent.boxType)
        }
    }

    fun getSendBoxes() {
        viewModelScope.launch(Dispatchers.IO) {
            getHomeBoxPaginationUseCase.getHomeBoxes("sent")
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { sendBox ->
                    setState {
                        it.copy(
                            sendBox = sendBox
                        )
                    }
                }
        }
    }

    fun getReceiveBoxes() {
        viewModelScope.launch(Dispatchers.IO) {
            getHomeBoxPaginationUseCase.getHomeBoxes("received")
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { receiveBox ->
                    setState {
                        it.copy(
                            receiveBox = receiveBox
                        )
                    }
                }
        }
    }

    fun getLazyBoxes() {
        viewModelScope.launch(Dispatchers.IO) {
            getLazyBoxUseCase.getLazyBox()
                .filterSuccess()
                .unwrapResource()
                .collect { lazyBox ->
                    setState {
                        it.copy(
                            lazyBox = lazyBox
                        )
                    }
                }
        }
    }
}