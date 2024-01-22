package com.packy.createbox.boxaddinfo

import com.packy.core.values.Constant.MAX_NICK_NAME_LENGTH
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoxAddInfoViewModel @Inject constructor() :
    MviViewModel<BoxAddInfoIntent, BoxAddInfoState, BoxAddInfoEffect>() {
    override fun createInitialState(): BoxAddInfoState = BoxAddInfoState(
        toName = "",
        fromName = ""
    )

    override fun handleIntent() {
        subscribeIntent<BoxAddInfoIntent.OnBackClick> {
            sendEffect(BoxAddInfoEffect.MoveToBack)
        }
        subscribeIntent<BoxAddInfoIntent.OnSaveButtonClick> {
            sendEffect(BoxAddInfoEffect.SaveBoxInfo)
        }
        subscribeStateIntent<BoxAddInfoIntent.ChangeToName> { state, intent ->
            if (intent.toName.length <= MAX_NICK_NAME_LENGTH) {
                state.copy(toName = intent.toName)
            } else {
                state
            }
        }
        subscribeStateIntent<BoxAddInfoIntent.ChangeFromName> { state, intent ->
            if (intent.fromName.length <= MAX_NICK_NAME_LENGTH) {
                state.copy(fromName = intent.fromName)
            } else {
                state
            }
        }
    }
}