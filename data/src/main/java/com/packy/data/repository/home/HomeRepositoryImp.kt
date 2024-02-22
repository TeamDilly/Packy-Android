package com.packy.data.repository.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.packy.data.model.home.toEntity
import com.packy.data.remote.home.HomeService
import com.packy.data.remote.home.MyBoxPagingSource
import com.packy.domain.model.home.HomeBox
import com.packy.domain.model.home.LazyBox
import com.packy.domain.model.home.NoticeGiftBox
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
        type: String,
        size: Int
    ): Flow<Resource<List<HomeBox>>> = flow {
        emit(Resource.Loading())
        val homeBoxContentDto = homeService.getHomoBoxes(
            type = type,
            size = size
        )
        emit(homeBoxContentDto.map { data -> data.content.map { it.toEntity() } })
    }

    override suspend fun getHomeBoxes(
        type: String
    ): Flow<PagingData<HomeBox>> {
        return Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = {
                MyBoxPagingSource(
                    homeService,
                    type
                )
            }
        ).flow
    }

    override suspend fun getLazyBox(): Flow<Resource<List<LazyBox>>> = flow {
        emit(Resource.Loading())
        val lazyBoxes = homeService.getLazyBoxes()
        emit(lazyBoxes.map { it.map { it.toEntity() } })
    }

    override suspend fun getNoticeGiftBox(): Flow<Resource<NoticeGiftBox?>> = flow {
        emit(Resource.Loading())
        val noticeGiftBox = homeService.getNoticeGiftBox()
        emit(noticeGiftBox.map { it?.toEntity() })
    }
}