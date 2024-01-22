package com.packy.data.remote.youtube

import com.packy.data.model.youtube.YoutubeInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class YoutubeService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getYoutubeInfo(
        url: String,
    ): YoutubeInfoDto = httpClient.get {
        url("oembed")
        parameter("url", url)
        parameter("format", "json")
    }.body()
}