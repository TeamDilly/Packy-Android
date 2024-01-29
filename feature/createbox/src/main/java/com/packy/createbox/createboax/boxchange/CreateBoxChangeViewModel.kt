package com.packy.createbox.createboax.boxchange

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxChangeViewModel @Inject constructor() :
    MviViewModel<CreateBoxChangeIntent, CreateBoxChangeState, CreateBoxChangeEffect>() {
    override fun createInitialState(): CreateBoxChangeState = CreateBoxChangeState(
        currentBox = null
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxChangeIntent.OnConfirmClick> {
            sendEffect(CreateBoxChangeEffect.OnConfirmClick)
        }
        subscribeStateIntent<CreateBoxChangeIntent.ChangeBox> { state, intent ->
            state.copy(currentBox = intent.box)
        }
    }
}