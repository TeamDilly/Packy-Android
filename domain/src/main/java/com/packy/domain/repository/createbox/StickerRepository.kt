package com.packy.domain.repository.createbox

import androidx.paging.PagingData
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.Sticker
import com.packy.domain.model.createbox.StickerList
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StickerRepository {
    suspend fun getSticker(): Flow<PagingData<Sticker>>
}