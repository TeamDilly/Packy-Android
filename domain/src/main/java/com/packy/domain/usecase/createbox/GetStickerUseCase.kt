package com.packy.domain.usecase.createbox

import com.packy.domain.model.createbox.Sticker

interface GetStickerUseCase {
    suspend fun getSticker(id: Int): List<Sticker>
}