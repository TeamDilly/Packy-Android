package com.packy.domain.usecase.createbox

import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetKakaoMessageImageUseCase {
    suspend fun getKakaoMessageImage(giftBoxId: Long): Flow<Resource<String>>
}