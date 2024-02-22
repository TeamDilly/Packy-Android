package com.packy.data.usecase.home

import com.packy.domain.model.home.NoticeGiftBox
import com.packy.domain.repository.home.HomeRepository
import com.packy.domain.usecase.home.GetNoticeGiftBoxUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeGiftBoxUseCaseImp @Inject constructor(
    private val homeRepository: HomeRepository
) : GetNoticeGiftBoxUseCase {
    override suspend fun getNoticeGiftBox(): Flow<Resource<NoticeGiftBox?>> = homeRepository.getNoticeGiftBox()
}