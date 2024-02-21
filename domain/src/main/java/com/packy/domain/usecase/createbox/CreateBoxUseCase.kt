package com.packy.domain.usecase.createbox

import com.packy.domain.model.box.CreatedBox
import com.packy.domain.model.createbox.box.CreateBox
import com.packy.lib.utils.Resource

interface CreateBoxUseCase {
    suspend fun getCreatedBox(): CreateBox
    suspend fun setCreateBox(createBox: CreateBox): Unit

    suspend fun createBox(createBox: CreateBox) : Resource<CreatedBox>
}