package com.packy.domain.usecase.youtube

import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ValidationYoutubeUrlUseCase {
    suspend fun validationYoutubeUrl(url: String): Flow<Resource<Boolean>>
}