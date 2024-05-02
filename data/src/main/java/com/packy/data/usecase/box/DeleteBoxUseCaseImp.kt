package com.packy.data.usecase.box

import com.packy.domain.repository.box.BoxRepository
import com.packy.domain.usecase.box.DeleteBoxUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBoxUseCaseImp @Inject constructor(
    private val repository: BoxRepository,
) : DeleteBoxUseCase {
    override suspend fun deleteBox(giftBoxId: Long): Flow<Resource<Unit>> =
        repository.deleteBox(giftBoxId)
}