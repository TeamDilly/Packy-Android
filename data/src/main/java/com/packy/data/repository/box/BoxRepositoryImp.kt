package com.packy.data.repository.box

import com.packy.data.model.toEntity
import com.packy.data.remote.box.BoxService
import com.packy.domain.model.box.BoxDesign
import com.packy.domain.repository.box.BoxRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BoxRepositoryImp @Inject constructor(
    private val api: BoxService
) : BoxRepository {
    override suspend fun getBoxDesign(): Flow<Resource<List<BoxDesign>>> = flow {
        emit(Resource.Loading())
        val boxService = api.getBoxDesign()
        emit(boxService.map { boxDesign ->
            boxDesign.map { it.toEntity() }
        })
    }
}