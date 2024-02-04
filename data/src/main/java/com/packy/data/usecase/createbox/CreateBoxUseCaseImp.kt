package com.packy.data.usecase.createbox

import com.packy.common.kakaoshare.KakaoCustomFeed
import com.packy.common.kakaoshare.KakaoShare
import com.packy.domain.model.box.BoxId
import com.packy.domain.model.createbox.box.CreateBox
import com.packy.domain.repository.box.BoxRepository
import com.packy.domain.repository.createbox.CreateBoxRepository
import com.packy.domain.usecase.box.BoxDesignUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreateBoxUseCaseImp @Inject constructor(
    private val repository: CreateBoxRepository,
    private val boxRepository: BoxRepository,
    private val kakaoShare: KakaoShare
) : CreateBoxUseCase {
    override suspend fun getCreatedBox(): CreateBox = repository.getCreatedBox()

    override suspend fun setCreateBox(createBox: CreateBox) {
        repository.setCreateBox(createBox)
    }

    override suspend fun createBox(createBox: CreateBox): Resource<BoxId> = repository.createBox(createBox)

}