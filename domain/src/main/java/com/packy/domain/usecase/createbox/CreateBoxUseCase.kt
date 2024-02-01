package com.packy.domain.usecase.createbox

import com.packy.domain.model.box.BoxId
import com.packy.domain.model.createbox.box.CreateBox

interface CreateBoxUseCase {
    suspend fun getCreatedBox(): CreateBox
    suspend fun setCreateBox(createBox: CreateBox): Unit

    suspend fun createBox(createBox: CreateBox) : BoxId
}