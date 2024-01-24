package com.packy.domain.usecase.box

import com.packy.domain.model.box.BoxDesign
import kotlinx.coroutines.flow.Flow

interface BoxDesignUseCase {
    suspend fun getBoxDesign(): Flow<BoxDesign?>
}