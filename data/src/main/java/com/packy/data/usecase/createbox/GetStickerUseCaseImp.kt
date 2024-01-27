package com.packy.data.usecase.createbox

import com.packy.domain.model.createbox.StickerList
import com.packy.domain.repository.createbox.StickerRepository
import com.packy.domain.usecase.createbox.GetStickerUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStickerUseCaseImp @Inject constructor(
    private val repository: StickerRepository
) : GetStickerUseCase {
    override suspend fun getSticker(id: Int?): Flow<Resource<StickerList>> =
        repository.getSticker(id)
}