package com.packy.createbox.createboax.addlatter

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxLatterViewModel @Inject constructor() :
    MviViewModel<CreateBoxLatterIntent, CreateBoxLatterState, CreateBoxLatterEffect>() {
    override fun createInitialState(): CreateBoxLatterState = CreateBoxLatterState(
        latterText = "",
        envelopeId = 1,
        envelopeList = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxLatterIntent.OnCloseClick> {
            sendEffect(CreateBoxLatterEffect.CloseBottomSheet)
        }
        subscribeIntent<CreateBoxLatterIntent.OnSaveClick> {
            sendEffect(CreateBoxLatterEffect.SaveLatter)
        }
        subscribeStateIntent<CreateBoxLatterIntent.ChangeEnvelope> { state, intent ->
            state.copy(envelopeId = intent.envelopeId)
        }
        subscribeStateIntent<CreateBoxLatterIntent.ChangeLatterText> { state, intent ->
            state.copy(latterText = intent.latter)
        }
    }
}