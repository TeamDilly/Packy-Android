package com.packy.domain.usecase.my

import kotlinx.coroutines.flow.Flow

interface GetMyNickNameUseCase {
    suspend fun getNickName(): Flow<String?>
}