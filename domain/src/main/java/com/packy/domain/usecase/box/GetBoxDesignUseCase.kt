package com.packy.domain.usecase.box

import com.packy.domain.model.box.BoxDesign
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetBoxDesignUseCase {
    suspend fun getBoxDesign(): Flow<Resource<List<BoxDesign>>>
    suspend fun getBoxDesignLocal(): Flow<BoxDesign?>
    suspend fun setBoxDesignLocal(boxDesign: BoxDesign): Unit
}