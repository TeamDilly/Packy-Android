package com.packy.domain.usecase.youtube

import com.packy.domain.model.youtube.YoutubeInfo

interface YoutubeUseCase {
    suspend fun getYoutubeInfo(
        url: String
    ): YoutubeInfo
}