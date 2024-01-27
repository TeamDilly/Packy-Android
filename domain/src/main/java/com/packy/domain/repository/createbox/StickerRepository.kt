package com.packy.domain.repository.createbox

import com.packy.domain.model.createbox.Sticker

interface StickerRepository {
    suspend fun getSticker(id: Int): List<Sticker>
}