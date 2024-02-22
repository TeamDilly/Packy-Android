package com.packy.data.repository.box

import com.packy.data.local.AccountPrefManager
import com.packy.data.model.createbox.BoxDesignDto
import com.packy.data.model.createbox.toEntity
import com.packy.domain.model.getbox.GiftBox
import com.packy.data.model.getbox.toEntity
import com.packy.data.remote.box.BoxService
import com.packy.domain.model.box.BoxDeliverStatus
import com.packy.domain.model.box.BoxDesign
import com.packy.domain.repository.box.BoxRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BoxRepositoryImp @Inject constructor(
    private val api: BoxService,
    private val prefManager: AccountPrefManager,
) : BoxRepository {
    override suspend fun getBox(giftBoxId: String): Flow<Resource<GiftBox>> = flow {
        emit(Resource.Loading())
        val giftBoxDto = api.getGifBox(giftBoxId)
        emit(giftBoxDto.map { it.toEntity(giftBoxId.toLong()) })
    }

    override suspend fun getBoxDesignLocal(): Flow<BoxDesign?> =
        prefManager.boxDesign.getData().map { it?.toEntity() }

    override suspend fun setBoxDesignLocal(boxDesign: BoxDesign) {
        prefManager.boxDesign.putData(
            BoxDesignDto(
                id = boxDesign.id,
                sequence = boxDesign.sequence,
                boxNormal = boxDesign.boxNormal,
                boxSet = boxDesign.boxSet,
                boxSmall = boxDesign.boxSmall,
                boxTop = boxDesign.boxTop
            )
        )
    }

    override suspend fun getBoxDesign(): Flow<Resource<List<BoxDesign>>> = flow {
        emit(Resource.Loading())
        val boxService = api.getBoxDesign()
        emit(boxService.map { boxDesign ->
            boxDesign.map { it.toEntity() }
        })
    }

    override suspend fun deleteBox(giftBoxId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val deleteBox = api.deleteBox(giftBoxId)
        emit(deleteBox.map { })
    }

    override suspend fun updateBoxDeliverStatus(giftBoxId: String, status: BoxDeliverStatus): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val deleteBox = api.updateBoxDeliverStatus(giftBoxId, status)
        emit(deleteBox.map { })
    }
}