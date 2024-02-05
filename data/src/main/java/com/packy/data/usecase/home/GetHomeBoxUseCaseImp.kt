package com.packy.data.usecase.home

import com.packy.domain.model.home.HomeBox
import com.packy.domain.repository.home.HomeRepository
import com.packy.domain.usecase.home.GetHomeBoxUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeBoxUseCaseImp @Inject constructor(
    private val repository: HomeRepository
): GetHomeBoxUseCase {
    override suspend fun getHomeBox(): Flow<Resource<List<HomeBox>>> = repository.getHomeBox(
        timestamp = null,
        type = "all",
        size = 6
    )
}