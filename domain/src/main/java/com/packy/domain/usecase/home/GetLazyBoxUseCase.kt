package com.packy.domain.usecase.home

import com.packy.domain.model.home.LazyBox
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetLazyBoxUseCase {
    suspend fun getLazyBox(): Flow<Resource<List<LazyBox>>>
}