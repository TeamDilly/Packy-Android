package com.packy.data.repository.music

import com.packy.data.remote.music.MusicService
import com.packy.domain.model.music.Music
import com.packy.domain.repository.music.MusicRepository
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicRepositoryImp @Inject constructor(
    private val api : MusicService
): MusicRepository {
    override suspend fun suggestionMusic(): Flow<Resource<List<Music>>> {
        api.suggestionMusic()
        return flow {
            // FIXME
        }
    }

}