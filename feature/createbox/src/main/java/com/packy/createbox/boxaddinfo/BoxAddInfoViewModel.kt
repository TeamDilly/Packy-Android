package com.packy.createbox.boxaddinfo

import androidx.lifecycle.viewModelScope
import com.packy.core.values.Constant.MAX_NICK_NAME_LENGTH
import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.domain.usecase.letter.GetLetterSenderReceiverUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxAddInfoViewModel @Inject constructor(
    private val useCase: GetLetterSenderReceiverUseCase
) :
    MviViewModel<BoxAddInfoIntent, BoxAddInfoState, BoxAddInfoEffect>() {
    override fun createInitialState(): BoxAddInfoState = BoxAddInfoState(
        LetterSenderReceiver(
            sender = "",
            receiver = ""
        )
    )

    override fun handleIntent() {
        subscribeIntent<BoxAddInfoIntent.OnBackClick> {
            sendEffect(BoxAddInfoEffect.MoveToBack)
        }
        subscribeIntent<BoxAddInfoIntent.OnSaveButtonClick> {
            setLetterSenderReceiver()
            sendEffect(BoxAddInfoEffect.SaveBoxInfo)
        }
        subscribeStateIntent<BoxAddInfoIntent.ChangeReceiver> { state, intent ->
            if (intent.receiver.length <= MAX_NICK_NAME_LENGTH) {
                val newLaterSenderReceiver = state.letterSenderReceiver.copy(
                    receiver = intent.receiver
                )
                state.copy(letterSenderReceiver = newLaterSenderReceiver)
            } else {
                state
            }
        }
        subscribeStateIntent<BoxAddInfoIntent.ChangeSender> { state, intent ->
            if (intent.sender.length <= MAX_NICK_NAME_LENGTH) {
                val newLaterSenderReceiver = state.letterSenderReceiver.copy(
                    sender = intent.sender
                )
                state.copy(letterSenderReceiver = newLaterSenderReceiver)
            } else {
                state
            }
        }
    }

    fun getLetterSenderReceiver() {
        viewModelScope.launch {
            useCase.getLetterSenderReceiver()
                .take(1)
                .collect {
                    setState(currentState)
                }
        }
    }

    private fun setLetterSenderReceiver() {
        viewModelScope.launch {
            useCase.setLetterSenderReceiver(
                currentState.letterSenderReceiver
            )
        }
    }

}