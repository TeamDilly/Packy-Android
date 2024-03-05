package com.packy.data.usecase.youtube

import com.packy.domain.repository.youtube.YoutubeRepository
import com.packy.domain.usecase.youtube.ValidationYoutubeUrlUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidationYoutubeUrlUseCaseImp @Inject constructor(
    private val repository: YoutubeRepository
) : ValidationYoutubeUrlUseCase {
    override suspend fun validationYoutubeUrl(url: String): Flow<Resource<Boolean>> =
        repository.validationYoutubeUrl(url)
}