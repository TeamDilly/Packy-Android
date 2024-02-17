package com.packy.domain.repository.archive

import androidx.paging.PagingData
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.model.archive.ArchiveMusic
import com.packy.domain.model.archive.ArchivePhoto
import kotlinx.coroutines.flow.Flow

interface ArchiveRepository {
    suspend fun getArchivePhoto(): Flow<PagingData<ArchivePhoto>>
    suspend fun getArchiveLetter(): Flow<PagingData<ArchiveLetter>>
    suspend fun getArchiveMusic(): Flow<PagingData<ArchiveMusic>>
    suspend fun getArchiveGift(): Flow<PagingData<ArchiveGift>>
}