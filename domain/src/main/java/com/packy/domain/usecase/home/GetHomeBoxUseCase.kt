package com.packy.domain.usecase.home

import com.packy.domain.model.home.HomeBox
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetHomeBoxUseCase {
    suspend fun getHomeBox(): Flow<Resource<List<HomeBox>>>
}