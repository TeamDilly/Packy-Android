package com.packy.data.usecase.home

import com.packy.domain.model.banner.ImageBanner
import com.packy.domain.repository.home.HomeRepository
import com.packy.domain.usecase.home.GetNoticesUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticesUseCaseImp @Inject constructor(
    private val homeRepository: HomeRepository
) : GetNoticesUseCase {
    override suspend fun getNotices(): Flow<Resource<List<ImageBanner>>> = homeRepository.getNotices()
}