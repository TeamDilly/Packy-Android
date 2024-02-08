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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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
                .firstOrNull()
                ?.sortedBy { it.sequence }
            envelopeList?.let {
                emitIntent(
                    CreateBoxLetterIntent.GetEnvelope(it)
                )
            }
        }
    }

    override fun createInitialState(): CreateBoxLetterState = CreateBoxLetterState(
        letterText = "",
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
                        LetterText = currentState.letterText
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
                    state.copy(letterText = intent.Letter)
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