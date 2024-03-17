package com.packy.domain.usecase.usable

import com.packy.domain.model.usable.Usable
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetUsableUseCase {
    suspend fun getUsable(
        version: String,
    ): Flow<Resource<Usable>>
}