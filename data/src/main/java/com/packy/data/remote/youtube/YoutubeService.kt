package com.packy.data.remote.youtube

import com.packy.data.model.youtube.YoutubeInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {

    @GET("oembed")
    suspend fun getYoutubeInfo(
        @Query("url") url: String,
        @Query("format") format: String = "json"
    ): YoutubeInfoDto
}