package com.packy.data.repository.youtube

import com.packy.data.model.youtube.toEntity
import com.packy.data.remote.youtube.YoutubeService
import com.packy.domain.model.youtube.YoutubeInfo
import com.packy.domain.repository.youtube.YoutubeRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class YoutubeRepositoryImp @Inject constructor(
    private val api: YoutubeService
) : YoutubeRepository {
    override suspend fun getYoutubeInfo(
        url: String
    ): YoutubeInfo = api.getYoutubeInfo(url).toEntity()

    override suspend fun validationYoutubeUrl(url: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val validation = api.checkValidationYoutubeUrl(url)
        emit(validation.map { it.status })
    }
}