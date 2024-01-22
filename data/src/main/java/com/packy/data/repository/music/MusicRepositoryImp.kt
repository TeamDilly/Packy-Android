package com.packy.data.repository.music

import com.packy.data.model.createbox.toEntity
import com.packy.data.model.music.toEntity
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicRepositoryImp @Inject constructor(
    private val api: MusicService,
    private val youtubeApi: YoutubeService
) : MusicRepository {
    override suspend fun suggestionMusic(): Flow<Resource<List<Music>>> = flow {
        CoroutineScope(Dispatchers.IO).launch {
            println("LOGEE $youtubeApi")
            emit(Resource.Loading())
            val suggestionMusic = api.suggestionMusic()
            if (suggestionMusic is Resource.Success) {
                val youtubeDeferredList = suggestionMusic.data.map {
                    async {
                        val youTubeInfo = youtubeApi.getYoutubeInfo(it.youtubeUrl)
                        println("LOGEE $youTubeInfo")
                    }
                }
                val youtubeInfo = youtubeDeferredList.awaitAll()
                suggestionMusic.map {

                }
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

}