package com.packy.data.remote.archive

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.model.archive.ArchivePhoto
import com.packy.lib.utils.Resource

class ArchiveLetterPagingSource(
    private val api: ArchiveService,
): PagingSource<Int, ArchiveLetter>() {
    override fun getRefreshKey(state: PagingState<Int, ArchiveLetter>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArchiveLetter> {
        val lastId = params.key
        val letterResource = api.getArchiveLetters()
        val result = if (letterResource is Resource.Success) {
            val archivePhotos = letterResource.data.content.map { it.toEntity() }
            LoadResult.Page(
                data = archivePhotos,
                prevKey = lastId,
                nextKey = if (letterResource.data.last) null else letterResource.data.content.last().id
            )
        } else {
            LoadResult.Error(Exception(letterResource.message))
        }
        return result
    }
}

