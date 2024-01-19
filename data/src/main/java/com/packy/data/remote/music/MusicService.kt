package com.packy.data.remote.music

import com.packy.data.model.music.SuggestionMusic
import com.packy.lib.utils.Resource
import retrofit2.http.GET

interface MusicService {

    @GET("api/v1/admin/music")
    fun suggestionMusic(): Resource<List<SuggestionMusic>>
}