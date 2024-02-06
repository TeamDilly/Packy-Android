package com.packy.domain.repository.home

import androidx.paging.PagingData
import com.packy.domain.model.home.HomeBox
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
}