package com.packy.data.usecase.box

import com.packy.domain.model.box.BoxDesign
import com.packy.domain.repository.box.BoxRepository
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoxDesignUseCaseImp @Inject constructor(
    private val repository: BoxRepository
) : GetBoxDesignUseCase {
    override suspend fun getBoxDesign(): Flow<Resource<List<BoxDesign>>> = repository.getBoxDesign()
}