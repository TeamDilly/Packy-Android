package com.packy.domain.repository.createbox

import com.packy.domain.model.createbox.box.CreateBox
import kotlinx.coroutines.flow.Flow

interface CreateBoxRepository {
    suspend fun shouldShowBoxMotion(): Flow<Boolean>
    suspend fun shownShowBoxMotion(): Unit

    suspend fun shouldShowBoxTutorial(): Flow<Boolean>
    suspend fun shownShowBoxTutorial(): Unit

    suspend fun createBox(createBox: CreateBox): Unit

    suspend fun getCreatedBox(): CreateBox
}