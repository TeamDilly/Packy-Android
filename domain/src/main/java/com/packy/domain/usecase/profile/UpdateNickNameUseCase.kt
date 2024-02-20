package com.packy.domain.usecase.profile

import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UpdateNickNameUseCase {
    suspend fun updateNickName(nickname: String): Flow<Resource<Unit>>
}