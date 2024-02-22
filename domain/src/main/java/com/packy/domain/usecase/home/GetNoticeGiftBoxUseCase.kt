package com.packy.domain.usecase.home

import com.packy.domain.model.home.NoticeGiftBox
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetNoticeGiftBoxUseCase {
    suspend fun getNoticeGiftBox(): Flow<Resource<NoticeGiftBox?>>
}