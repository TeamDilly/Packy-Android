package com.packy.data.remote.archive

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.packy.domain.model.archive.ArchivePhoto
import com.packy.lib.utils.Resource

class ArchivePhotoPagingSource(
    private val api: ArchiveService,
): PagingSource<Int, ArchivePhoto>() {
    override fun getRefreshKey(state: PagingState<Int, ArchivePhoto>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArchivePhoto> {
        val lastId = params.key
        val photoResource = api.getArchivePhotos(lastId)
        val result = if (photoResource is Resource.Success) {
            val archivePhotos = photoResource.data.content.map { it.toEntity() }
            LoadResult.Page(
                data = archivePhotos,
                prevKey = lastId,
                nextKey = if (photoResource.data.last) null else photoResource.data.content.last().id
            )
        } else {
            LoadResult.Error(Exception(photoResource.message))
        }
        return result
    }
}

