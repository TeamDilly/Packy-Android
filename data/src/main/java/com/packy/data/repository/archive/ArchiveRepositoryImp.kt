package com.packy.data.repository.archive

import androidx.paging.PagingData
import com.packy.data.remote.archive.ArchiveService
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.model.archive.ArchiveMusic
import com.packy.domain.model.archive.ArchivePhoto
import com.packy.domain.repository.archive.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveRepositoryImp @Inject constructor(
    private val archiveService: ArchiveService
): ArchiveRepository{
    override suspend fun getArchivePhoto(): Flow<PagingData<ArchivePhoto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArchiveLetter(): Flow<PagingData<ArchiveLetter>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArchiveMusic(): Flow<PagingData<ArchiveMusic>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArchiveGift(): Flow<PagingData<ArchiveGift>> {
        TODO("Not yet implemented")
    }

}