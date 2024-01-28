package com.packy.data.repository.music

import com.packy.data.model.createbox.toEntity
import com.packy.data.model.music.SuggestionMusicDto
import com.packy.data.model.music.toEntity
import com.packy.data.model.youtube.YoutubeInfoDto
import com.packy.data.remote.music.MusicService
import com.packy.data.remote.youtube.YoutubeService
import com.packy.domain.model.music.Music
import com.packy.domain.repository.music.MusicRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicRepositoryImp @Inject constructor(
    private val api: MusicService,
    private val youtubeService: YoutubeService
) : MusicRepository {
    override suspend fun suggestionMusic(): Flow<Resource<List<Music>>> = flow {
        emit(Resource.Loading())
        val suggestionMusic = api.suggestionMusic()
        if (suggestionMusic is Resource.Success) {
            emit(suggestionMusic.map { it.toEntity() })
        } else {
            emit(
                Resource.ApiError(
                    data = null,
                    code = suggestionMusic.code,
                    message = suggestionMusic.message
                )
            )
        }
    }
}