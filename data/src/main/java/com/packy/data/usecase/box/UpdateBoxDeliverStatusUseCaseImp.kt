package com.packy.data.usecase.box

import com.packy.domain.model.box.BoxDeliverStatus
import com.packy.domain.repository.box.BoxRepository
import com.packy.domain.usecase.box.UpdateBoxDeliverStatusUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateBoxDeliverStatusUseCaseImp @Inject constructor(
    private val repository: BoxRepository
) : UpdateBoxDeliverStatusUseCase {
    override suspend fun updateBoxDeliverStatus(
        giftBoxId: Long,
        status: BoxDeliverStatus
    ): Flow<Resource<Unit>> = repository.updateBoxDeliverStatus(giftBoxId, status)
}