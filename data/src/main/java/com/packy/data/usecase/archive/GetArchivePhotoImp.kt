package com.packy.data.usecase.archive

import androidx.paging.PagingData
import com.packy.domain.model.archive.ArchivePhoto
import com.packy.domain.repository.archive.ArchiveRepository
import com.packy.domain.usecase.archive.GetArchivePhoto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArchivePhotoImp @Inject constructor(
    private val archiveRepository: ArchiveRepository
) : GetArchivePhoto {
    override suspend fun getArchivePhoto(): Flow<PagingData<ArchivePhoto>> =
        archiveRepository.getArchivePhoto()
}