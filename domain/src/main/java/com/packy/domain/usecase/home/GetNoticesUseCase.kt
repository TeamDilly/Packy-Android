package com.packy.domain.usecase.home

import com.packy.domain.model.banner.ImageBanner
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetNoticesUseCase {
    suspend fun getNotices(): Flow<Resource<List<ImageBanner>>>
}