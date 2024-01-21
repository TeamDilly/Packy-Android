package com.packy.createbox.createboax.addlatter

import androidx.lifecycle.viewModelScope
import com.packy.core.values.Constant
import com.packy.domain.usecase.createbox.LatterUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.map
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxLatterViewModel @Inject constructor(
    private val latterUseCase: LatterUseCase
) :
    MviViewModel<CreateBoxLatterIntent, CreateBoxLatterState, CreateBoxLatterEffect>() {

    fun getLatterEnvelope() {
        viewModelScope.launch {
            latterUseCase.getLatterEnvelope()
                .filterSuccess()
                .collect {
                    val envelopeList = it.data.map { latterEnvelop ->
                        LatterEnvelopeItem(
                            id = latterEnvelop.id,
                            imageUri = latterEnvelop.envelope
                        )
                    }
                    emitIntent(
                        CreateBoxLatterIntent.GetEnvelope(envelopeList)
                    )
                }
        }
    }

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
            println("intent.latter.length ${intent.latter.length}")
            println("intent.latter ${intent.latter.lines().size}")
            if (intent.latter.length <= Constant.MAX_LATTER_TEXT) {
                if (intent.latter.lines().size <= Constant.MAX_LATTER_LINES + 1) {
                    state.copy(latterText = intent.latter)
                } else {
                    state
                }
            } else {
                sendEffect(CreateBoxLatterEffect.OverFlowLatterText)
                state
            }
        }
        subscribeStateIntent<CreateBoxLatterIntent.GetEnvelope> { state, intent ->
            state.copy(envelopeList = intent.envelopeList)
        }
    }
}