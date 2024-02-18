package com.packy.data.usecase.archive

import androidx.paging.PagingData
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.model.archive.ArchiveMusic
import com.packy.domain.repository.archive.ArchiveRepository
import com.packy.domain.usecase.archive.GetArchiveGift
import com.packy.domain.usecase.archive.GetArchiveMusic
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArchiveMusicImp @Inject constructor(
    private val archiveRepository: ArchiveRepository
) : GetArchiveMusic {
    override suspend fun getArchiveMusic(): Flow<PagingData<ArchiveMusic>> =
        archiveRepository.getArchiveMusic()
}