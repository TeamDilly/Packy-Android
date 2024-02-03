package com.packy.domain.usecase.box

import com.packy.data.model.getbox.GiftBox
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetBoxUseCase {
    suspend fun getBox(giftBoxId: String): Flow<Resource<GiftBox>>
}