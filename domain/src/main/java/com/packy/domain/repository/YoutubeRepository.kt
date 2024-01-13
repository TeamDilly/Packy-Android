package com.packy.domain.repository

import com.packy.domain.model.youtube.YoutubeInfo

interface YoutubeRepository {
    suspend fun getYoutubeInfo(
        url: String
    ): YoutubeInfo
}