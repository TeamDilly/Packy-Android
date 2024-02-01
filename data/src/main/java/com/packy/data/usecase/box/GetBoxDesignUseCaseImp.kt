package com.packy.data.usecase.box

import com.packy.domain.model.box.BoxDesign
import com.packy.domain.repository.box.BoxRepository
import com.packy.domain.repository.createbox.CreateBoxRepository
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoxDesignUseCaseImp @Inject constructor(
    private val repository: BoxRepository,
    private val createBoxRepository: CreateBoxRepository
) : GetBoxDesignUseCase {
    override suspend fun getBoxDesign(): Flow<Resource<List<BoxDesign>>> = repository.getBoxDesign()
    override suspend fun getBoxDesignLocal(): Flow<BoxDesign?> = repository.getBoxDesignLocal()

    override suspend fun setBoxDesignLocal(boxDesign: BoxDesign) {
        repository.setBoxDesignLocal(boxDesign)
        val createdBox = createBoxRepository.getCreatedBox()
        createBoxRepository.setCreateBox(createdBox.copy(boxId = boxDesign.id))
    }
}