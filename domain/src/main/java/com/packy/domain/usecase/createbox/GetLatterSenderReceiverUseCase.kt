package com.packy.domain.usecase.createbox

import com.packy.domain.model.createbox.LetterSenderReceiver
import kotlinx.coroutines.flow.Flow

interface GetLetterSenderReceiverUseCase {
    suspend fun getLetterSenderReceiver(): Flow<LetterSenderReceiver?>

    suspend fun setLetterSenderReceiver(letterSenderReceiver: LetterSenderReceiver): Unit
}