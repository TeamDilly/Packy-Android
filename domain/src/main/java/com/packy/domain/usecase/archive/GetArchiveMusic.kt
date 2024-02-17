package com.packy.domain.usecase.archive

import androidx.paging.PagingData
import com.packy.domain.model.archive.ArchiveMusic
import kotlinx.coroutines.flow.Flow

interface GetArchiveMusic {
    suspend fun getArchiveMusic(): Flow<PagingData<ArchiveMusic>>
}