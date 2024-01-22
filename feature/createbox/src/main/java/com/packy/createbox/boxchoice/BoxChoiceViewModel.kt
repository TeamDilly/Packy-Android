package com.packy.createbox.boxchoice

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoxChoiceViewModel @Inject constructor() :
    MviViewModel<BoxChoiceIntent, BoxChoiceState, BoxChoiceEffect>() {
    override fun createInitialState(): BoxChoiceState = BoxChoiceState(
        selectedBox = null,
        boxDesignList = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<BoxChoiceIntent.OnBackClick> { sendEffect(BoxChoiceEffect.MoveToBack) }
        subscribeIntent<BoxChoiceIntent.OnCloseClick> { sendEffect(BoxChoiceEffect.CloseCreateBox) }
        subscribeIntent<BoxChoiceIntent.OnSaveClick> { sendEffect(BoxChoiceEffect.SaveBoxInfo) }
        subscribeStateIntent<BoxChoiceIntent.ChangeSelectBox> { state, intent ->
            state.copy(selectedBox = intent.selectedBox)
        }
    }
}