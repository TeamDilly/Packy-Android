package com.packy.createbox.boxaddinfo

import androidx.lifecycle.viewModelScope
import com.packy.core.values.Constant.MAX_NICK_NAME_LENGTH
import com.packy.createbox.common.receiverName
import com.packy.createbox.common.senderName
import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.domain.usecase.letter.GetLetterSenderReceiverUseCase
import com.packy.domain.usecase.my.GetMyNickNameUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxAddInfoViewModel @Inject constructor(
    private val useCase: CreateBoxUseCase,
    private val getMyNickNameUseCase: GetMyNickNameUseCase
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
            val defaultNickName = getMyNickNameUseCase.getNickName().firstOrNull()
            val createBox = useCase.getCreatedBox()
            val receiverName = createBox.receiverName
            val senderName = createBox.senderName ?: defaultNickName
            val newLetterSenderReceiver = currentState.letterSenderReceiver.copy(
                sender = senderName ?: "",
                receiver = receiverName ?: ""
            )
            setState(
                currentState.copy(
                    letterSenderReceiver = newLetterSenderReceiver
                )
            )
        }
    }

    private fun setLetterSenderReceiver() {
        viewModelScope.launch {
            useCase.senderName(currentState.letterSenderReceiver.sender)
            useCase.receiverName(currentState.letterSenderReceiver.receiver)
        }
    }

}