package com.packy.data.usecase.archive

import androidx.paging.PagingData
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.repository.archive.ArchiveRepository
import com.packy.domain.usecase.archive.GetArchiveLetter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArchiveLetterImp @Inject constructor(
    private val archiveRepository: ArchiveRepository
) : GetArchiveLetter {
    override suspend fun getArchiveLetter(): Flow<PagingData<ArchiveLetter>> =
        archiveRepository.getArchiveLetter()
}