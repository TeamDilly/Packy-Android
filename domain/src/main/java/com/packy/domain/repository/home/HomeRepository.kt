package com.packy.domain.repository.home

import com.packy.domain.model.home.HomeBox
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHomeBox(
        timestamp: String?,
        type: String,
        size: Int
    ): Flow<Resource<List<HomeBox>>>
}