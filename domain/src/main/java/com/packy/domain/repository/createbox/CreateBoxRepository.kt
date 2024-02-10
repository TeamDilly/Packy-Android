package com.packy.domain.repository.createbox

import com.packy.domain.model.box.BoxId
import com.packy.domain.model.createbox.box.CreateBox
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CreateBoxRepository {
    suspend fun shouldShowBoxMotion(): Flow<Boolean>
    suspend fun shownShowBoxMotion(): Unit

    suspend fun shouldShowBoxTutorial(): Flow<Boolean>
    suspend fun shownShowBoxTutorial(): Unit

    suspend fun shouldShowBoxSharMotion(): Flow<Boolean>
    suspend fun shownShowBoxSharMotion(): Unit

    suspend fun setCreateBox(createBox: CreateBox): Unit

    suspend fun getCreatedBox(): CreateBox

    suspend fun createBox(createBox: CreateBox) : Resource<BoxId>
}