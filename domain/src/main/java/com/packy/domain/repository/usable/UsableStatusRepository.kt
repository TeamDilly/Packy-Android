package com.packy.domain.repository.usable

import com.packy.domain.model.usable.Usable
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UsableStatusRepository {
    suspend fun getUsable(
        version: String,
    ): Flow<Resource<Usable>>
}