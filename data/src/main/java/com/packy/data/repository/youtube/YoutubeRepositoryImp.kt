package com.packy.data.repository.youtube

import com.packy.data.model.youtube.toEntity
import com.packy.data.remote.youtube.YoutubeService
import com.packy.domain.model.youtube.YoutubeInfo
import com.packy.domain.repository.youtube.YoutubeRepository
import javax.inject.Inject

class YoutubeRepositoryImp @Inject constructor(
    private val api: YoutubeService
) : YoutubeRepository {
    override suspend fun getYoutubeInfo(
        url: String
    ): YoutubeInfo = api.getYoutubeInfo(url).toEntity()
}