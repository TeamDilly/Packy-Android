package com.packy.data.usecase.home

import androidx.paging.PagingData
import com.packy.domain.model.home.HomeBox
import com.packy.domain.repository.home.HomeRepository
import com.packy.domain.usecase.home.GetHomeBoxPaginationUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeBoxPaginationUseCaseImp @Inject constructor(
    private val repositoryImp: HomeRepository
): GetHomeBoxPaginationUseCase {
    override suspend fun getHomeBoxes(
        type: String
    ): Flow<PagingData<HomeBox>> = repositoryImp.getHomeBoxes(type)
}