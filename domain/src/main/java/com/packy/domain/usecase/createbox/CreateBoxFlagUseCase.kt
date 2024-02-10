package com.packy.domain.usecase.createbox

import kotlinx.coroutines.flow.Flow

interface CreateBoxFlagUseCase {
    suspend fun shouldShowBoxMotion(): Flow<Boolean>
    suspend fun shownShowBoxMotion(): Unit

    suspend fun shouldShowBoxTutorial(): Flow<Boolean>
    suspend fun shownShowBoxTutorial(): Unit

    suspend fun shouldShowBoxSharMotion(): Flow<Boolean>
    suspend fun shownShowBoxSharMotion(): Unit
}