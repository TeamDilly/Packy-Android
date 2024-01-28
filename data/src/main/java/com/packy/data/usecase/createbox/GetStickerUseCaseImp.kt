package com.packy.data.usecase.createbox

import androidx.paging.PagingData
import com.packy.domain.model.createbox.Sticker
import com.packy.domain.model.createbox.StickerList
import com.packy.domain.repository.createbox.StickerRepository
import com.packy.domain.usecase.createbox.GetStickerUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStickerUseCaseImp @Inject constructor(
    private val repository: StickerRepository
) : GetStickerUseCase {
    override suspend fun getSticker(): Flow<PagingData<Sticker>> =
        repository.getSticker()
}