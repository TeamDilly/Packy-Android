package com.packy.data.usecase.youtube

import com.packy.domain.model.youtube.YoutubeInfo
import com.packy.domain.repository.youtube.YoutubeRepository
import com.packy.domain.usecase.youtube.YoutubeUseCase
import javax.inject.Inject

class YoutubeUseCaseImp @Inject constructor(
    private val repository: YoutubeRepository
) : YoutubeUseCase {
    override suspend fun getYoutubeInfo(url: String): YoutubeInfo = repository.getYoutubeInfo(url)
}