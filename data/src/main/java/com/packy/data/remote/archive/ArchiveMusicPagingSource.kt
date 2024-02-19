package com.packy.data.remote.archive

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.packy.data.remote.youtube.YoutubeService
import com.packy.domain.model.archive.ArchiveMusic
import com.packy.lib.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class ArchiveMusicPagingSource(
    private val api: ArchiveService,
    private val youtubeApi: YoutubeService
) : PagingSource<Int, ArchiveMusic>() {
    override fun getRefreshKey(state: PagingState<Int, ArchiveMusic>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArchiveMusic> {
        val lastId = params.key
        val musicResource = api.getArchiveMusics(lastId)
        val result = if (musicResource is Resource.Success) {
            val youtubeThumbnails = withContext(Dispatchers.IO) {
                musicResource.data.content.map {
                    async {
                        val thumbnail = getThumbnails(it.youtubeUrl)
                        if (thumbnail != null) {
                            it.toEntity(thumbnail)
                        } else {
                            null
                        }
                    }
                }.awaitAll()
            }
            LoadResult.Page(
                data = youtubeThumbnails.filterNotNull(),
                prevKey = lastId,
                nextKey = if (musicResource.data.last) null else musicResource.data.content.last().giftBoxId
            )
        } else {
            LoadResult.Error(Exception(musicResource.message))
        }
        return result
    }

    private suspend fun getThumbnails(youtubeId: String): String? {
        return try {
            val youtubeInfo = youtubeApi.getYoutubeInfo(youtubeId)
            youtubeInfo.thumbnailUrl
        } catch (e: Exception) {
            null
        }
    }
}

