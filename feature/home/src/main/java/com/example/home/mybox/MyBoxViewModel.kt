package com.example.home.mybox

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.packy.domain.usecase.box.DeleteBoxUseCase
import com.packy.domain.usecase.home.GetHomeBoxPaginationUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.loadingHandler
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBoxViewModel @Inject constructor(
    private val getHomeBoxPaginationUseCase: GetHomeBoxPaginationUseCase,
    private val deleteBoxUseCase: DeleteBoxUseCase
) :
    MviViewModel<MyBoxIntent, MyBoxState, MyBoxEffect>() {

    override fun createInitialState(): MyBoxState = MyBoxState(
        showTab = MyBoxType.SEND,
        sendBox = PagingData.empty(),
        receiveBox = PagingData.empty()
    )

    override fun handleIntent() {
        subscribeIntent<MyBoxIntent.OnBackClick> { sendEffect(MyBoxEffect.MoveToBack) }
        subscribeIntent<MyBoxIntent.ClickMyBox> { sendEffect(MyBoxEffect.MoveToBoxDetail(it.boxId)) }
        subscribeIntent<MyBoxIntent.OnMyBoxMoreClick> { sendEffect(MyBoxEffect.ShowDeleteBottomSheet(it.boxId)) }
        subscribeIntent<MyBoxIntent.OnClickDeleteMyBoxBottomSheet> { sendEffect(MyBoxEffect.ShowDeleteDialog(it.boxId)) }
        subscribeIntent<MyBoxIntent.OnDeleteBoxClick> { intent ->
            deleteBoxUseCase.deleteBox(intent.boxId.toString())
                .loadingHandler { setState { state -> state.copy(isLoading = it) } }
                .filterSuccess()
                .collect {
                    setState { state ->
                        state.copy(
                            removeItemBox = state.removeItemBox + intent.boxId
                        )
                    }
                }
        }
        subscribeStateIntent<MyBoxIntent.ChangeShowBoxType> { state, intent ->
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
                            sendBox =  sendBox
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
}