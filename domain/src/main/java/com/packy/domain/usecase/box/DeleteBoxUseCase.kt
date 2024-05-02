package com.packy.domain.usecase.box

import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DeleteBoxUseCase {
    suspend fun deleteBox(giftBoxId: Long): Flow<Resource<Unit>>
}