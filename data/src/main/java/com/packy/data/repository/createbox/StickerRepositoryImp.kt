package com.packy.data.repository.createbox

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.packy.data.model.createbox.toEntity
import com.packy.data.remote.createbox.StickerPagingSource
import com.packy.data.remote.createbox.StickerService
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.Sticker
import com.packy.domain.model.createbox.StickerList
import com.packy.domain.repository.createbox.StickerRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StickerRepositoryImp @Inject constructor(
    private val stickerService: StickerService
) : StickerRepository {
    override suspend fun getSticker(selectedSticker: SelectedSticker): Flow<PagingData<Sticker>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                StickerPagingSource(stickerService, selectedSticker)
            }
        ).flow
    }
}