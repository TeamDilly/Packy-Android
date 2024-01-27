package com.packy.data.repository.createbox

import com.packy.data.model.createbox.toEntity
import com.packy.data.remote.createbox.StickerService
import com.packy.domain.model.createbox.StickerList
import com.packy.domain.repository.createbox.StickerRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StickerRepositoryImp @Inject constructor(
    private val stickerService: StickerService
) : StickerRepository {
    override suspend fun getSticker(id: Int?): Flow<Resource<StickerList>> = flow {
        emit(Resource.Loading())
        val stickerResource = stickerService.getSticker(id)
        emit(stickerResource.map { stickerDto ->
            stickerDto.toEntity()
        })
    }
}