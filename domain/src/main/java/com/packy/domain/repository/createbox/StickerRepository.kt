package com.packy.domain.repository.createbox

import com.packy.domain.model.createbox.Sticker
import com.packy.domain.model.createbox.StickerList

interface StickerRepository {
    suspend fun getSticker(id: Int): StickerList
}