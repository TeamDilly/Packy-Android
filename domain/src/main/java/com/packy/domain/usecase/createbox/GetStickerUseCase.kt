package com.packy.domain.usecase.createbox

import com.packy.domain.model.createbox.StickerList
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetStickerUseCase {
    suspend fun getSticker(id: Int?): Flow<Resource<StickerList>>
}