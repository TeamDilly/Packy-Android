package com.packy.domain.usecase.createbox

import com.packy.domain.model.createbox.LetterSenderReceiver
import kotlinx.coroutines.flow.Flow

interface GetLatterSenderReceiverUseCase {
    suspend fun getLatterSenderReceiver(): Flow<LetterSenderReceiver?>

    suspend fun setLatterSenderReceiver(letterSenderReceiver: LetterSenderReceiver): Unit
}