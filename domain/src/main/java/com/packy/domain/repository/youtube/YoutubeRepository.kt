package com.packy.domain.repository.youtube

import com.packy.domain.model.youtube.YoutubeInfo

interface YoutubeRepository {
    suspend fun getYoutubeInfo(
        url: String
    ): YoutubeInfo
}