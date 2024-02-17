package com.packy.domain.usecase.archive

import androidx.paging.PagingData
import com.packy.domain.model.archive.ArchivePhoto
import kotlinx.coroutines.flow.Flow

interface GetArchivePhoto {
    suspend fun getArchivePhoto(): Flow<PagingData<ArchivePhoto>>
}