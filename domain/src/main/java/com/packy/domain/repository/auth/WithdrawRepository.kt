package com.packy.domain.repository.auth

import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WithdrawRepository {
    suspend fun withdraw(): Flow<Resource<Unit>>
}