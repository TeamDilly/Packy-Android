package com.packy.data.usecase.createbox

import com.packy.domain.model.createbox.LatterEnvelope
import com.packy.domain.repository.createbox.LatterRepository
import com.packy.domain.usecase.createbox.LatterUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LatterUseCaseImp @Inject constructor(
    private val latterRepository: LatterRepository
):  LatterUseCase{
    override suspend fun getLatterEnvelope(): Flow<Resource<LatterEnvelope>> =
        latterRepository.getLatterEnvelope()
}