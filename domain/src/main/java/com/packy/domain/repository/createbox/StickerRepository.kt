package com.packy.domain.repository.createbox

import com.packy.domain.model.createbox.Sticker
import com.packy.domain.model.createbox.StickerList
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StickerRepository {
    suspend fun getSticker(id: Int?): Flow<Resource<StickerList>>
}