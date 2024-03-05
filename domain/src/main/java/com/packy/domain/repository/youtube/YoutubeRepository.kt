package com.packy.domain.repository.youtube

import com.packy.domain.model.youtube.YoutubeInfo
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface YoutubeRepository {
    suspend fun getYoutubeInfo(url: String): YoutubeInfo

    suspend fun validationYoutubeUrl(url: String): Flow<Resource<Boolean>>
}