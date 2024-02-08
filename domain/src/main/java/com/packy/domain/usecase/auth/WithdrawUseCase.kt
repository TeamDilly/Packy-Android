package com.packy.domain.usecase.auth

import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WithdrawUseCase {
    suspend fun withdraw(): Flow<Resource<Unit>>
}