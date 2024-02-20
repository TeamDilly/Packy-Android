package com.packy.domain.usecase.box

import com.packy.domain.model.box.BoxDeliverStatus
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UpdateBoxDeliverStatusUseCase {
    suspend fun updateBoxDeliverStatus(
        giftBoxId: String,
        status: BoxDeliverStatus
    ): Flow<Resource<Unit>>
}