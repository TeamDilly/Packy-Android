package com.packy.domain.usecase.archive

import androidx.paging.PagingData
import com.packy.domain.model.archive.ArchiveLetter
import kotlinx.coroutines.flow.Flow

interface GetArchiveLetter {
    suspend fun getArchiveLetter(): Flow<PagingData<ArchiveLetter>>
}