package com.packy.domain.usecase.createbox

import com.packy.domain.model.createbox.Sticker
import com.packy.domain.model.createbox.StickerList

interface GetStickerUseCase {
    suspend fun getSticker(id: Int): StickerList
}