package com.packy.domain.repository.box

import com.packy.domain.model.box.BoxDeliverStatus
import com.packy.domain.model.getbox.GiftBox
import com.packy.domain.model.box.BoxDesign
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BoxRepository {
    suspend fun getBox(giftBoxId: String): Flow<Resource<GiftBox>>
    suspend fun getBoxDesignLocal(): Flow<BoxDesign?>
    suspend fun setBoxDesignLocal(boxDesign: BoxDesign): Unit
    suspend fun getBoxDesign(): Flow<Resource<List<BoxDesign>>>

    suspend fun deleteBox(giftBoxId: String): Flow<Resource<Unit>>

    suspend fun updateBoxDeliverStatus(
        giftBoxId: Long,
        status: BoxDeliverStatus
    ): Flow<Resource<Unit>>
}