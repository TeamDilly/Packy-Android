package com.packy.createbox.boxtitle

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoxAddTitleViewModel @Inject constructor() :
    MviViewModel<BoxAddTitleIntent, BoxAddTitleState, BoxAddTitleEffect>() {
    override fun createInitialState(): BoxAddTitleState = BoxAddTitleState(
        boxTitle = ""
    )

    override fun handleIntent() {
        subscribeIntent<BoxAddTitleIntent.OnBackClick> {
            sendEffect(BoxAddTitleEffect.MoveToBack)
        }
        subscribeStateIntent<BoxAddTitleIntent.OnTitleChange> { state, intent ->
            state.copy(boxTitle = intent.text)
        }
        subscribeIntent<BoxAddTitleIntent.MoveToNext> {
            sendEffect(BoxAddTitleEffect.SaveBoxTitle(currentState.boxTitle))
        }
    }
}