package com.packy.domain.repository.music

import com.packy.domain.model.music.Music
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun suggestionMusic(): Flow<Resource<List<Music>>>
}