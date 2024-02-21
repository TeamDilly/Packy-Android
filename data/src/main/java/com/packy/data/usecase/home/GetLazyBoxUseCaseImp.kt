package com.packy.data.usecase.home

import com.packy.domain.model.home.LazyBox
import com.packy.domain.repository.home.HomeRepository
import com.packy.domain.usecase.home.GetLazyBoxUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLazyBoxUseCaseImp @Inject constructor(
    private val repositoryImp: HomeRepository
): GetLazyBoxUseCase {
    override suspend fun getLazyBox(): Flow<Resource<List<LazyBox>>> = repositoryImp.getLazyBox()
}