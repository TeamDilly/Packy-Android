package com.packy.data.remote.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.packy.data.model.createbox.toEntity
import com.packy.data.model.home.toEntity
import com.packy.domain.model.home.HomeBox
import com.packy.lib.utils.Resource

class MyBoxPagingSource(
    private val api: HomeService,
    private val type: String
) : PagingSource<String, HomeBox>() {
    override fun getRefreshKey(state: PagingState<String, HomeBox>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, HomeBox> {
        val lastTimestamp = params.key
        val homeBoxResource = api.getHomoBoxes(
            timestamp = lastTimestamp,
            type = type,
            size = 10
        )
        val result = if (homeBoxResource is Resource.Success) {
            val homeBoxes = homeBoxResource.data.content.map { it.toEntity() }
            LoadResult.Page(
                data = homeBoxes,
                prevKey = lastTimestamp,
                nextKey = if (homeBoxResource.data.content.size > 1) null else homeBoxResource.data.content.last().giftBoxDate
            )
        } else {
            LoadResult.Error(Exception(homeBoxResource.message))
        }
        return result
    }
}