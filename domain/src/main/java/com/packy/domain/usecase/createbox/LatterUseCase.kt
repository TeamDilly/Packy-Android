package com.packy.domain.usecase.createbox

import com.packy.domain.model.createbox.LatterEnvelope
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LatterUseCase {
    suspend fun getLatterEnvelope() : Flow<Resource<List<LatterEnvelope>>>
}