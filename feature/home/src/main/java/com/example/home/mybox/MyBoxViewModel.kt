package com.example.home.mybox

import androidx.paging.PagingData
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyBoxViewModel @Inject constructor() :
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
}