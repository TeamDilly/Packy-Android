package com.packy.data.repository.home

import com.packy.data.model.home.toEntity
import com.packy.data.remote.home.HomeService
import com.packy.domain.model.home.HomeBox
import com.packy.domain.repository.home.HomeRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(
    private val homeService: HomeService
) : HomeRepository {
    override suspend fun getHomeBox(
        timestamp: String?,
        type: String,
        size: Int
    ): Flow<Resource<List<HomeBox>>> = flow {
        emit(Resource.Loading())
        val homeBoxContentDto = homeService.getHomoBoxes(
            timestamp = timestamp,
            type = type,
            size = size
        )
        emit(homeBoxContentDto.map { it.content.map { it.toEntity() } })
    }
}