package com.packy.domain.repository.createbox

import com.packy.domain.model.createbox.LatterEnvelope
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LatterRepository {
    suspend fun getLatterEnvelope() : Flow<Resource<List<LatterEnvelope>>>
}