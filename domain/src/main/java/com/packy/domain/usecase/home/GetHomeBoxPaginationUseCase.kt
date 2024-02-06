package com.packy.domain.usecase.home

import androidx.paging.PagingData
import com.packy.domain.model.home.HomeBox
import kotlinx.coroutines.flow.Flow

interface GetHomeBoxPaginationUseCase {
    suspend fun getHomeBoxes(
        type: String,
    ): Flow<PagingData<HomeBox>>
}