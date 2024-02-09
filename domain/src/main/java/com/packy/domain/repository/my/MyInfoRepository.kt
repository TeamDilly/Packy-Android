package com.packy.domain.repository.my

import kotlinx.coroutines.flow.Flow

interface MyInfoRepository {
    suspend fun getMyNickName(): Flow<String?>
}