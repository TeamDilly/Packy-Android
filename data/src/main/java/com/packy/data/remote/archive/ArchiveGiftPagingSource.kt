package com.packy.data.remote.archive

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.packy.domain.model.archive.ArchiveGift
import com.packy.lib.utils.Resource

class ArchiveGiftPagingSource(
    private val api: ArchiveService,
) : PagingSource<Int, ArchiveGift>() {
    override fun getRefreshKey(state: PagingState<Int, ArchiveGift>): Int? {
        return null
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, ArchiveGift> {
        val lastId = params.key
        val letterResource = api.getArchiveGifts(lastId)
        val result = if (letterResource is Resource.Success) {
            val archivePhotos = letterResource.data.content.map { it.toEntity() }
            PagingSource.LoadResult.Page(
                data = archivePhotos,
                prevKey = lastId,
                nextKey = if (letterResource.data.last) null else letterResource.data.content.last().giftBoxId
            )
        } else {
            PagingSource.LoadResult.Error(Exception(letterResource.message))
        }
        return result
    }
}

