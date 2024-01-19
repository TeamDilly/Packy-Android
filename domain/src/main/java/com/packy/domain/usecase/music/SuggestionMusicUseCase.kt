package com.packy.domain.usecase.music

import com.packy.domain.model.music.Music
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SuggestionMusicUseCase {
    suspend fun suggestionMusic(): Flow<Resource<List<Music>>>
}