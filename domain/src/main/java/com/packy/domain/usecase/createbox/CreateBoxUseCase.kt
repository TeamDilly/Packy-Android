package com.packy.domain.usecase.createbox

import com.packy.domain.model.createbox.box.CreateBox

interface CreateBoxUseCase {
    suspend fun getCreatedBox(): CreateBox
    suspend fun createBox(createBox: CreateBox): Unit
}