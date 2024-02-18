package com.packy.data.repository.archive

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.packy.data.remote.archive.ArchiveGiftPagingSource
import com.packy.data.remote.archive.ArchiveLetterPagingSource
import com.packy.data.remote.archive.ArchiveMusicPagingSource
import com.packy.data.remote.archive.ArchivePhotoPagingSource
import com.packy.data.remote.archive.ArchiveService
import com.packy.data.remote.youtube.YoutubeService
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.model.archive.ArchiveMusic
import com.packy.domain.model.archive.ArchivePhoto
import com.packy.domain.repository.archive.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveRepositoryImp @Inject constructor(
    private val archiveService: ArchiveService,
    private val youtubeService: YoutubeService
) : ArchiveRepository {
    override suspend fun getArchivePhoto(): Flow<PagingData<ArchivePhoto>> = Pager(
        config = PagingConfig(pageSize = 6),
        pagingSourceFactory = {
            ArchivePhotoPagingSource(
                archiveService
            )
        }
    ).flow

    override suspend fun getArchiveLetter(): Flow<PagingData<ArchiveLetter>> = Pager(
        config = PagingConfig(pageSize = 6),
        pagingSourceFactory = {
            ArchiveLetterPagingSource(
                archiveService
            )
        }
    ).flow

    override suspend fun getArchiveMusic(): Flow<PagingData<ArchiveMusic>> = Pager(
        config = PagingConfig(pageSize = 6),
        pagingSourceFactory = {
            ArchiveMusicPagingSource(
                archiveService,
                youtubeService
            )
        }
    ).flow

    override suspend fun getArchiveGift(): Flow<PagingData<ArchiveGift>> = Pager(
        config = PagingConfig(pageSize = 6),
        pagingSourceFactory = {
            ArchiveGiftPagingSource(
                archiveService
            )
        }
    ).flow

}