package com.packy.domain.usecase.createbox

import com.packy.domain.model.createbox.LetterEnvelope
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LetterUseCase {
    suspend fun getLetterEnvelope() : Flow<Resource<List<LetterEnvelope>>>
}