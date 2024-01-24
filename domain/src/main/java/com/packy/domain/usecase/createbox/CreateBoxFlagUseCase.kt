package com.packy.domain.usecase.createbox

import kotlinx.coroutines.flow.Flow

interface CreateBoxFlagUseCase {
    suspend fun shouldShowBoxMotion(): Flow<Boolean>
    suspend fun shouldShowBoxTutorial(): Flow<Boolean>
}