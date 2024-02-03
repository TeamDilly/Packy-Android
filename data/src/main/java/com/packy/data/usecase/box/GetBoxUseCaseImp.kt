package com.packy.data.usecase.box

import com.packy.data.model.getbox.GiftBox
import com.packy.domain.repository.box.BoxRepository
import com.packy.domain.usecase.box.GetBoxUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoxUseCaseImp @Inject constructor(
    private val repository: BoxRepository
) : GetBoxUseCase {
    override suspend fun getBox(giftBoxId: String): Flow<Resource<GiftBox>> =
        repository.getBox(giftBoxId)
}