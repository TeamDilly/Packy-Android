package com.packy.domain.repository.box

import com.packy.domain.model.box.BoxDesign
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BoxRepository {
    suspend fun getBoxDesignLocal(): Flow<BoxDesign?>
    suspend fun setBoxDesignLocal(boxDesign: BoxDesign): Unit
    suspend fun getBoxDesign(): Flow<Resource<List<BoxDesign>>>
}