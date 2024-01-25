package com.packy.createbox.createboax.addlatter

import androidx.lifecycle.viewModelScope
import com.packy.core.values.Constant
import com.packy.createbox.createboax.addLetter.CreateBoxLetterEffect
import com.packy.createbox.createboax.addLetter.CreateBoxLetterIntent
import com.packy.createbox.createboax.addLetter.CreateBoxLetterState
import com.packy.domain.usecase.letter.LetterUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxLetterViewModel @Inject constructor(
    private val letterUseCase: LetterUseCase
) :
    MviViewModel<CreateBoxLetterIntent, CreateBoxLetterState, CreateBoxLetterEffect>() {

    fun getLetterEnvelope() {
        viewModelScope.launch {
            val envelopeList = letterUseCase.getLetterEnvelope()
                .filterSuccess()
                .unwrapResource()
                .single()
                .sortedBy { it.sequence }
            emitIntent(
                CreateBoxLetterIntent.GetEnvelope(envelopeList)
            )
        }
    }

    override fun createInitialState(): CreateBoxLetterState = CreateBoxLetterState(
        LetterText = "",
        envelopeId = 1,
        envelopeList = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxLetterIntent.OnCloseClick> {
            sendEffect(CreateBoxLetterEffect.CloseBottomSheet)
        }
        subscribeIntent<CreateBoxLetterIntent.OnSaveClick> {
            val envelopeItem = currentState.getLetterEnvelope()
            if (envelopeItem != null) {
                sendEffect(
                    CreateBoxLetterEffect.SaveLetter(
                        envelopId = envelopeItem.id,
                        envelopUri = envelopeItem.imgUrl,
                        LetterText = currentState.LetterText
                    )
                )
            }
        }
        subscribeStateIntent<CreateBoxLetterIntent.ChangeEnvelope> { state, intent ->
            state.copy(envelopeId = intent.envelopeId)
        }
        subscribeStateIntent<CreateBoxLetterIntent.ChangeLetterText> { state, intent ->
            if (intent.Letter.length <= Constant.MAX_Letter_TEXT) {
                if (intent.Letter.lines().size <= Constant.MAX_Letter_LINES + 1) {
                    state.copy(LetterText = intent.Letter)
                } else {
                    state
                }
            } else {
                sendEffect(CreateBoxLetterEffect.OverFlowLetterText)
                state
            }
        }
        subscribeStateIntent<CreateBoxLetterIntent.GetEnvelope> { state, intent ->
            state.copy(envelopeList = intent.envelopeList)
        }
    }
}