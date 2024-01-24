package com.packy.createbox.createboax.addlatter

import androidx.lifecycle.viewModelScope
import com.packy.core.values.Constant
import com.packy.domain.usecase.createbox.LatterUseCase
import com.packy.lib.utils.catchError
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.map
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxLatterViewModel @Inject constructor(
    private val latterUseCase: LatterUseCase
) :
    MviViewModel<CreateBoxLatterIntent, CreateBoxLatterState, CreateBoxLatterEffect>() {

    fun getLatterEnvelope() {
        viewModelScope.launch {
            val envelopeList = latterUseCase.getLatterEnvelope()
                .filterSuccess()
                .unwrapResource()
                .single()
                .sortedBy { it.sequence }
            emitIntent(
                CreateBoxLatterIntent.GetEnvelope(envelopeList)
            )
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
            val envelopeItem = currentState.getLatterEnvelope()
            if (envelopeItem != null) {
                sendEffect(
                    CreateBoxLatterEffect.SaveLatter(
                        envelopId = envelopeItem.id,
                        envelopUri = envelopeItem.imgUrl,
                        latterText = currentState.latterText
                    )
                )
            }
        }
        subscribeStateIntent<CreateBoxLatterIntent.ChangeEnvelope> { state, intent ->
            state.copy(envelopeId = intent.envelopeId)
        }
        subscribeStateIntent<CreateBoxLatterIntent.ChangeLatterText> { state, intent ->
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