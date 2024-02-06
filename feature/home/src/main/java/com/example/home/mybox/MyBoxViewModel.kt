package com.example.home.mybox

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.packy.domain.usecase.home.GetHomeBoxPaginationUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBoxViewModel @Inject constructor(
    private val getHomeBoxPaginationUseCase: GetHomeBoxPaginationUseCase
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
        subscribeStateIntent<MyBoxIntent.ChangeShowBoxType> { state, intent ->
            state.copy(showTab = intent.boxType)
        }
    }

    fun getSendBoxes(){
        viewModelScope.launch(Dispatchers.IO) {
            //FIXME: Change the type to "send"
            getHomeBoxPaginationUseCase.getHomeBoxes("all")
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    setState(
                        currentState.copy(
                            sendBox = it
                        )
                    )
                }
        }
    }

    fun getReceiveBoxes() {
        viewModelScope.launch(Dispatchers.IO) {
            // FIXME: Change the type to "receive"
            getHomeBoxPaginationUseCase.getHomeBoxes("all")
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    setState(
                        currentState.copy(
                            receiveBox = it
                        )
                    )
                }
        }
    }
}