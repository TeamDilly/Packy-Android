package com.packy.data.usecase.archive

import androidx.paging.PagingData
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.repository.archive.ArchiveRepository
import com.packy.domain.usecase.archive.GetArchiveGift
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArchiveGiftImp @Inject constructor(
    private val archiveRepository: ArchiveRepository
) : GetArchiveGift {
    override suspend fun getArchiveGift(): Flow<PagingData<ArchiveGift>> =
        archiveRepository.getArchiveGift()
}