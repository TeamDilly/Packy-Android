package com.packy.data.remote.music

import com.packy.data.model.music.SuggestionMusicDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class MusicService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun suggestionMusic(): Resource<List<SuggestionMusicDto>> = safeRequest {
        httpClient.get(urlString = "api/v1/admin/music")
    }
}