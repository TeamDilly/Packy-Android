package com.packy.domain.repository.home

import androidx.paging.PagingData
import com.packy.domain.model.home.HomeBox
import com.packy.domain.model.home.LazyBox
import com.packy.domain.model.home.NoticeGiftBox
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHomeBox(
        type: String,
        size: Int
    ): Flow<Resource<List<HomeBox>>>

    suspend fun getHomeBoxes(
        type: String,
    ): Flow<PagingData<HomeBox>>

    suspend fun getLazyBox(): Flow<Resource<List<LazyBox>>>

    suspend fun getNoticeGiftBox(): Flow<Resource<NoticeGiftBox?>>

    suspend fun getDeferredLinkBoxId(): Flow<Long?>
}