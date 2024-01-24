package com.packy.domain.repository.letter

import com.packy.domain.model.createbox.LetterEnvelope
import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LetterRepository {
    suspend fun getLetterEnvelope() : Flow<Resource<List<LetterEnvelope>>>

    suspend fun getLetterSenderReceiver() : Flow<LetterSenderReceiver?>

    suspend fun setLetterSenderReceiver(letterSenderReceiver: LetterSenderReceiver): Unit
}