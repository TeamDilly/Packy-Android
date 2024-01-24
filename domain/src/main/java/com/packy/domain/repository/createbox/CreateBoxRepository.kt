package com.packy.domain.repository.createbox

import kotlinx.coroutines.flow.Flow

interface CreateBoxRepository {
    suspend fun shouldShowBoxMotion(): Flow<Boolean>
    suspend fun shownShowBoxMotion(): Unit

    suspend fun shouldShowBoxTutorial(): Flow<Boolean>
    suspend fun shownShowBoxTutorial(): Unit
}