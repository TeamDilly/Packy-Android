package com.packy.domain.usecase.archive

import androidx.paging.PagingData
import com.packy.domain.model.archive.ArchiveGift
import kotlinx.coroutines.flow.Flow

interface GetArchiveGift {
    suspend fun getArchiveGift(): Flow<PagingData<ArchiveGift>>
}