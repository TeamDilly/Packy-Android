package com.packy.data.remote.createbox

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.packy.data.model.createbox.toEntity
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.Sticker
import com.packy.lib.utils.Resource
import javax.inject.Inject

class StickerPagingSource(
    private val api: StickerService,
    private val selectedSticker: SelectedSticker
) : PagingSource<Int, Sticker>() {
    override fun getRefreshKey(state: PagingState<Int, Sticker>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Sticker> {
        val lastId = params.key
        val stickersDto = api.getSticker(lastId)
        val result = if (stickersDto is Resource.Success) {
            val stickers = stickersDto.data.content.map { it.toEntity() }.filter {
                selectedSticker.notContains(it.id)
            }
            LoadResult.Page(
                data = stickers,
                prevKey = params.key,
                nextKey = if (stickersDto.data.last) null else stickers.last().id
            )
        } else {
            LoadResult.Error(Exception(stickersDto.message))
        }
        return result
    }
}