package com.packy.domain.usecase.createbox

import com.packy.domain.model.createbox.LetterSenderReceiver
import kotlinx.coroutines.flow.Flow

interface GetLatterSenderReceiver {
    suspend fun getLatterSenderReceiver() : Flow<LetterSenderReceiver?>
}